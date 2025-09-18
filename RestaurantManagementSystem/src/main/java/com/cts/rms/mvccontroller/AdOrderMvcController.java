package com.cts.rms.mvccontroller;

import com.cts.rms.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/Admin/Order")
public class AdOrderMvcController {
    @Autowired
    private OrderService orderService;
    
    @GetMapping
    public String listOrders(Model model) {
        model.addAttribute("orders", orderService.getAllOrder());
        return "Admin_Order";
    }

    @PostMapping("/delete/{id}")
    public String deleteOrder(@PathVariable("id") Long id, RedirectAttributes redirectAttributes) {
        if(orderService.deleteOrder(id)) {
            redirectAttributes.addFlashAttribute("message", "Order deleted successfully.");
        } else {
            redirectAttributes.addFlashAttribute("error", "Could not delete order.");
        }
        return "redirect:/Admin/Order";
    }
}