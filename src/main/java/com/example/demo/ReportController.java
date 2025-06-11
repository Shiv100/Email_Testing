package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ReportController {

    @Autowired
    private TrackingService trackingService;

    @GetMapping("/report")
    public String showReport(Model model) {
        model.addAttribute("records", trackingService.getAllRecords());
        return "report";  // Return the name of the Thymeleaf template file without .html
    }
}
