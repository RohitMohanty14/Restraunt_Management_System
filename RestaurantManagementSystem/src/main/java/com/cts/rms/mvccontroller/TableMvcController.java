
package com.cts.rms.mvccontroller;

import com.cts.rms.model.ReservationTable;
import com.cts.rms.model.User;
import com.cts.rms.service.TableReservationService;
import com.cts.rms.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/User/Table")
public class TableMvcController {

    @Autowired
    private TableReservationService tableService;

    @Autowired
    private UserService userService;

    // Method to get the currently logged-in user
    private User getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUserName = authentication.getName();
        return userService.findByEmail(currentUserName)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

    @GetMapping("/myReservations")
    public String listUserReservations(Model model) {
        User currentUser = getCurrentUser();
        List<ReservationTable> reservations = tableService.getAllReservations().stream()
                .filter(res -> res.getUser().getUserId() == (currentUser.getUserId()))
                .collect(Collectors.toList());
        model.addAttribute("reservations", reservations);
        return "User_Reservations"; // New HTML page for user's reservations
    }

    @GetMapping("/new")
    public String showReservationForm(Model model) {
        model.addAttribute("reservation", new ReservationTable());
        return "Reservation_form"; // New HTML page for the form
    }

    @PostMapping("/save")
    public String saveReservation(@ModelAttribute("reservation") ReservationTable reservation, RedirectAttributes redirectAttributes) {
        User currentUser = getCurrentUser();
        reservation.setUser(currentUser);
        tableService.saveReservation(reservation);
        redirectAttributes.addFlashAttribute("message", "Table reserved successfully!");
        return "redirect:/User/Table/myReservations";
    }

    @PostMapping("/delete/{id}")
    public String deleteReservation(@PathVariable("id") Long id, RedirectAttributes redirectAttributes) {
        tableService.deleteReservation(id);
        redirectAttributes.addFlashAttribute("message", "Reservation cancelled.");
        return "redirect:/User/Table/myReservations";
    }
}