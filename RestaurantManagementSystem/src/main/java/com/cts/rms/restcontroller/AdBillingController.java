//package com.cts.rms.restcontroller;
//import com.cts.rms.model.Billing;
//import com.cts.rms.service.BillingReservationService;
//import jakarta.validation.Valid;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.ResponseEntity;
//import org.springframework.validation.BindingResult;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.Map;
//
//@RestController
//@RequestMapping("/Admin/Billing")
//public class AdBillingController {
//	//all crud operation
//	@Autowired
//    private BillingReservationService billingService;
//
//    @GetMapping("/getAll")
//    public ResponseEntity<?> listAllBills() {
//        return ResponseEntity.ok(Map.of(
//            "message", "Bills retrieved successfully",
//            "bills", billingService.getAllBills()
//        ));
//    }
//	@GetMapping("/getById/{id}")
//    public ResponseEntity<?> listById(@PathVariable Long id) {
//        return ResponseEntity.ok(Map.of(
//            "message", "Bills retrieved successfully",
//            "users", billingService.getById(id)
//        ));
//    }
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
//    @DeleteMapping("/deleteBill/{id}")
//    public ResponseEntity<?> deleteBill(@PathVariable Long id) {
//        return ResponseEntity.ok(Map.of(
//            "message", "Bill Deleted Successfully",
//            "bill", billingService.getAllBills().stream()
//                .filter(b -> b.getBillingId() == id) // Note: assuming getBillingId() returns a compatible type
//                .findFirst(),
//            "value", billingService.deleteBill(id)
//        ));
//    }
//
//    @PutMapping("/updateBill/{id}")
//    public ResponseEntity<?> updateBill(@RequestBody @Valid Billing billing, @PathVariable Long id, BindingResult binding) {
//        if (binding.hasErrors()) {
//            return ResponseEntity.badRequest().body(Map.of("message", "Bill updating failed due to validation errors"));
//        }
//        return ResponseEntity.ok(Map.of(
//            "message", "Bill Updated Successfully",
//            "bill", billingService.updateBill(billing, id)
//        ));
//    }
//}
