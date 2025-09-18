
package com.cts.rms.mvccontroller;

import com.cts.rms.model.Billing;
import com.cts.rms.model.Orders;
import com.cts.rms.service.BillingReservationService;
import com.cts.rms.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/User/Billing")
public class BillingMvcController {

    @Autowired
    private BillingReservationService billingService;

    @Autowired
    private OrderService orderService;

   
    @PostMapping("/checkout")
    public String showCheckoutPage(@RequestParam(value = "orderIds", required = false) List<Long> orderIds, Model model, RedirectAttributes redirectAttributes) {
        if (orderIds == null || orderIds.isEmpty()) {
            redirectAttributes.addFlashAttribute("error", "Your cart is empty or no items were selected.");
            return "redirect:/User/Order/cart";
        }

        List<Orders> ordersToBill = orderIds.stream()
                                            .map(id -> orderService.getById(id))
                                            .collect(Collectors.toList());

        double totalAmount = ordersToBill.stream()
                                         .mapToDouble(order -> order.getMenu().getPrice() * order.getQuantity())
                                         .sum();

        model.addAttribute("ordersToBill", ordersToBill);
        model.addAttribute("totalAmount", totalAmount);
        model.addAttribute("orderIds", orderIds);

        return "Checkout"; 
    }

    @PostMapping("/generateForCart")
    public String createBillForCart(@RequestParam("orderIds") List<Long> orderIds,
                                    @RequestParam("paymentType") String paymentType,
                                    RedirectAttributes redirectAttributes) {

        if (orderIds == null || orderIds.isEmpty()) {
            redirectAttributes.addFlashAttribute("error", "Billing failed: No items to process.");
            return "redirect:/User/Order/cart";
        }

        // Generate one invoice number for the entire transaction
        int sharedInvoiceNumber = ThreadLocalRandom.current().nextInt(100000, 999999 + 1);
        double totalTransactionAmount = 0.0;

        List<Orders> ordersToBill = orderIds.stream()
                                            .map(id -> orderService.getById(id))
                                            .collect(Collectors.toList());

        for (Orders order : ordersToBill) {
            if (order != null && order.getBilling() == null) {
                Billing bill = new Billing();
                bill.setOrder(order);
                bill.setBillingDate(LocalDateTime.now());
                bill.setInvoiceNumber(sharedInvoiceNumber); // Use the shared invoice number
                bill.setPaymentType(paymentType);

                Billing savedBill = billingService.saveBill(bill);
                totalTransactionAmount += savedBill.getTotalAmount();
            }
        }

        if (totalTransactionAmount > 0) {
            redirectAttributes.addFlashAttribute("message",
                String.format("Payment Successful! Invoice #%d has been generated for a total of ₹%.2f.",
                              sharedInvoiceNumber, totalTransactionAmount));
        } else {
             redirectAttributes.addFlashAttribute("error", "These items have already been paid for.");
             return "redirect:/User/Order/cart";
        }


        return "redirect:/User/Menu/Home";
    }
}