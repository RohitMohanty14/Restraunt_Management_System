package com.cts.rms.mvccontroller;

import com.cts.rms.model.Menu;
import com.cts.rms.service.MenuService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/Admin/Menu")
public class AdMenuMvcController {

    @Autowired
    private MenuService menuService;

    @GetMapping
    public String listMenu(Model model) {
        model.addAttribute("menuList", menuService.getAllMenu());
        return "Admin_Menu";
    }

    @GetMapping("/add")
    public String showAddMenuForm(Model model) {
        model.addAttribute("menu", new Menu());
        return "Admin_Menu_form";
    }

    @PostMapping("/add")
    public String addMenu(@ModelAttribute("menu") @Valid Menu menu, BindingResult result, RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            return "Admin_Menu_form";
        }
        menuService.saveMenu(menu);
        redirectAttributes.addFlashAttribute("message", "Menu item added successfully!");
        return "redirect:/Admin/Menu";
    }

    @GetMapping("/edit/{id}")
    public String showEditMenuForm(@PathVariable("id") Long id, Model model) {
        Menu menu = menuService.getById(id);
        if (menu == null) {
            return "redirect:/Admin/Menu";
        }
        model.addAttribute("menu", menu);
        return "Admin_Menu_form";
    }

    @PostMapping("/update/{id}")
    public String updateMenu(@PathVariable("id") Long id, @ModelAttribute("menu") @Valid Menu menu, BindingResult result, RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            return "Admin_Menu_form";
        }
        menuService.updateMenu(menu, id);
        redirectAttributes.addFlashAttribute("message", "Menu item updated successfully!");
        return "redirect:/Admin/Menu";
    }

    @PostMapping("/delete/{id}")
    public String deleteMenu(@PathVariable("id") Long id, RedirectAttributes redirectAttributes) {
        menuService.deleteMenu(id);
        redirectAttributes.addFlashAttribute("message", "Menu item deleted successfully!");
        return "redirect:/Admin/Menu";
    }
}