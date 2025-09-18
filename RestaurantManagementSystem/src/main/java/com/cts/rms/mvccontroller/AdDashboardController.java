package com.cts.rms.mvccontroller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/Admin")
public class AdDashboardController {

    @GetMapping("/dashboard")
    public String showAdminDashboard() {
        return "Admin_dashboard";
    }
}