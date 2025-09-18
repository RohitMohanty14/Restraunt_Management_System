package com.cts.rms.mvccontroller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.cts.rms.service.MenuService;

@Controller
@RequestMapping("/User/Menu")
public class MenuMvcController {

    @Autowired
    private MenuService menuService;

    @GetMapping("/Home")
    public String listAllMenu(Model model) {
        if (!model.containsAttribute("message") && !model.containsAttribute("error")) {
            model.addAttribute("display", "Welcome to Our Restaurant!");
        }
        
        model.addAttribute("menuList", menuService.getAllMenu());
        return "Home";   
    }
}