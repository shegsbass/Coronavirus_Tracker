package dev.shegs.coronavirus.controllers;

import dev.shegs.coronavirus.models.LocationStat;
import dev.shegs.coronavirus.services.CoronaVirusDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

@Controller
public class HomeController {

    @Autowired
    CoronaVirusDataService coronaVirusDataService;

    @GetMapping("/")
    public String home(Model model){
        List<LocationStat> allStats = coronaVirusDataService.getAllStats();
        int totalReportedCases = allStats.stream().mapToInt(stat -> stat.getLatestTotalCases()).sum();
        String formattedTotalReportedCases = NumberFormat.getNumberInstance(Locale.US).format(totalReportedCases);

        int totalNewCases = allStats.stream().mapToInt(stat -> stat.getDiffFromPrevDay()).sum();
        String formattedTotalNewCases = NumberFormat.getNumberInstance(Locale.US).format(totalNewCases);

        model.addAttribute("locationStats", allStats);
        model.addAttribute("totalReportedCases", formattedTotalReportedCases);
        model.addAttribute("totalNewCases", formattedTotalNewCases);
        return "home";
    }
}
