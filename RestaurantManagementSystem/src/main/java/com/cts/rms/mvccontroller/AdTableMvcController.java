package com.cts.rms.mvccontroller;

import com.cts.rms.service.TableReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/Admin/Table")
public class AdTableMvcController {

    @Autowired
    private TableReservationService tableService;
    
    @GetMapping
    public String listReservations(Model model) {
        model.addAttribute("reservations", tableService.getAllReservations());
        return "Admin_Table";
    }

    @PostMapping("/delete/{id}")
    public String deleteReservation(@PathVariable("id") Long id, RedirectAttributes redirectAttributes) {
        if (tableService.deleteReservation(id)) {
            redirectAttributes.addFlashAttribute("message", "Reservation deleted successfully.");
        } else {
            redirectAttributes.addFlashAttribute("error", "Could not delete reservation.");
        }
        return "redirect:/Admin/Table";
    }
}