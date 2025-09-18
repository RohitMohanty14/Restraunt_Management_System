package com.cts.rms.mvccontroller;

import com.cts.rms.service.BillingReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/Admin/Billing")
public class AdBillingMvcController {

    @Autowired
    private BillingReservationService billingService;

    @GetMapping
    public String listBills(Model model) {
        model.addAttribute("bills", billingService.getAllBills());
        return "Admin_Billing";
    }

}