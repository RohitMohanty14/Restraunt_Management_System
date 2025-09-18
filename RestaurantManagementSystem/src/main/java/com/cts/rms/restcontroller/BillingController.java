//package com.cts.rms.restcontroller;
//
//import com.cts.rms.model.Billing;
////import com.cts.rms.model.Billing;
//import com.cts.rms.service.BillingReservationService;
//
//import jakarta.validation.Valid;
//
////import jakarta.validation.Valid;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.ResponseEntity;
//import org.springframework.validation.BindingResult;
////import org.springframework.validation.BindingResult;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.Map;
//
//@RestController
//@RequestMapping("/User/Billing")
//public class BillingController {
////all get method needed
//	
//    @Autowired
//    private BillingReservationService billingService;
//    
//    
//    @PostMapping("/generateBill")
//    public ResponseEntity<?> generateBill(@RequestBody @Valid Billing billing, BindingResult binding) {
//        if (binding.hasErrors()) {
//            return ResponseEntity.badRequest().body(Map.of("message", "Bill generation failed due to validation errors"));
//        }
//        return ResponseEntity.ok(Map.of(
//            "message", "Bill generated successfully",
//            "bill", billingService.saveBill(billing)
//        ));
//    }
//    
//	@GetMapping("/getById/{id}")
//    public ResponseEntity<?> listById(@PathVariable Long id) {
//        return ResponseEntity.ok(Map.of(
//            "message", "Bills retrieved successfully",
//            "users", billingService.getById(id)
//        ));
//    }
//}