package com.cts.rms.mvccontroller;


import com.cts.rms.model.Menu;
import com.cts.rms.model.Orders;
import com.cts.rms.model.User;
import com.cts.rms.service.BillingReservationService;
import com.cts.rms.service.MenuService;
import com.cts.rms.service.OrderService;
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
@RequestMapping("/User/Order")
public class OrderMvcController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private UserService userService;
    
    @Autowired
    private MenuService menuService;
    
    @Autowired
    private BillingReservationService billingReservationService;

    // Method to get the currently logged-in user
    private User getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUserName = authentication.getName();
        return userService.findByEmail(currentUserName)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

    @PostMapping("/addToCart")
    public String addToCart(@RequestParam("menuId") Long menuId, RedirectAttributes redirectAttributes ) {
        User currentUser = getCurrentUser();
        Menu menu = menuService.getById(menuId);

        if (menu == null) {
            redirectAttributes.addFlashAttribute("error", "Menu item not found!");
            return "redirect:/User/Menu/Home";
        }

 
        List<Orders> cartItems = orderService.getAllOrder().stream()
                .filter(order -> order.getUser().getUserId() == (currentUser.getUserId()) && order.getBilling() == null)
                .collect(Collectors.toList());

        boolean itemExistsInCart = cartItems.stream()
                .anyMatch(order -> order.getMenu().getMenuId().equals(menuId));

        if (itemExistsInCart) {
       
            redirectAttributes.addFlashAttribute("error", "'" + menu.getItemName() + "' is already in your cart.");
        } else {
         
            Orders order = new Orders();
            order.setUser(currentUser);
            order.setMenu(menu);
            order.setQuantity(1); // Default quantity
            orderService.saveOrder(order);
            redirectAttributes.addFlashAttribute("message", "Added '" + menu.getItemName() + "' to cart!");
        }
        
        return "redirect:/User/Menu/Home";
    }

    @GetMapping("/cart")
    public String viewCart(Model model) {
        User currentUser = getCurrentUser();
        // Fetch orders for the current user that haven't been billed yet
        List<Orders> cartItems = orderService.getAllOrder().stream()
                .filter(order -> order.getUser().getUserId() == (currentUser.getUserId()) && order.getBilling() == null)
                .collect(Collectors.toList());

        model.addAttribute("cartItems", cartItems);
        
        // Calculate total
        double total = cartItems.stream()
            .mapToDouble(order -> order.getMenu().getPrice() * order.getQuantity())
            .sum();
        model.addAttribute("totalAmount", total);
        
        return "Order_cart";
    }
    
    @PostMapping("/cart/update")
    public String updateCartItem(@RequestParam("orderId") Long orderId, @RequestParam("quantity") int quantity, RedirectAttributes redirectAttributes) {
        Orders order = orderService.getById(orderId);
        if (order != null && quantity > 0) {
            order.setQuantity(quantity);
            orderService.updateOrder(order, orderId);
            redirectAttributes.addFlashAttribute("message", "Cart updated!");
        } else {
            redirectAttributes.addFlashAttribute("error", "Invalid quantity or order not found.");
        }
        return "redirect:/User/Order/cart";
    }

    @PostMapping("/cart/delete/{orderId}")
    public String deleteCartItem(@PathVariable("orderId") Long orderId, RedirectAttributes redirectAttributes) {
        orderService.deleteOrder(orderId);
        redirectAttributes.addFlashAttribute("message", "Item removed from cart.");
        return "redirect:/User/Order/cart";
    }
    
    @GetMapping("/myOrders")
    public String viewMyOrders(Model model) {
        User currentUser = getCurrentUser();
        List<Orders> myOrders = orderService.getAllOrder().stream()
                .filter(order -> order.getUser().getUserId() == currentUser.getUserId())
                .peek(order -> order.setBilling(
                        billingReservationService.getAllBills().stream()
                                .filter(bill -> bill.getOrder().getOrderId().equals(order.getOrderId()))
                                .findFirst().orElse(null)))
                .collect(Collectors.toList());

        model.addAttribute("invoice", myOrders);
        return "Orders";
    }
}