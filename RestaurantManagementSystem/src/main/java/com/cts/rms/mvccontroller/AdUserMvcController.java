package com.cts.rms.mvccontroller;

import com.cts.rms.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/Admin/User")
public class AdUserMvcController {
    @Autowired
    private UserService userService;

    @GetMapping
    public String listUsers(Model model) {
        model.addAttribute("users", userService.listAllUsers());
        return "Admin_User";
    }
    
    @PostMapping("/delete/{id}")
    public String deleteUser(@PathVariable("id") Long id, RedirectAttributes redirectAttributes) {
        if(userService.removeUser(id)) {
            redirectAttributes.addFlashAttribute("message", "User deleted successfully.");
        } else {
            redirectAttributes.addFlashAttribute("error", "Could not delete user.");
        }
        return "redirect:/Admin/User";
    }
}