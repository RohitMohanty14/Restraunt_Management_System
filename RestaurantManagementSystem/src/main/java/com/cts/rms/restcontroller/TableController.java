//package com.cts.rms.restcontroller;
//
//import com.cts.rms.model.ReservationTable;
//import com.cts.rms.service.TableReservationService;
//
//import jakarta.validation.Valid;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.*;
//import org.springframework.http.ResponseEntity;
//import org.springframework.validation.BindingResult;
//
//
//import java.util.Map;
//
//@RestController
//@RequestMapping("/User/reservations")
//public class TableController {
//
//	
//	//all get method needed
//	
//	
//    @Autowired
//    private TableReservationService reservationService;
//
//    
//    @GetMapping("/getById/{id}")
//    public ResponseEntity<?> listById(@PathVariable Long id) {
//        return ResponseEntity.ok(Map.of(
//            "message", "Table retrieved successfully",
//            "users", reservationService.getById(id)
//        ));
//    }
//    @PostMapping("/bookTable")
//    public ResponseEntity<?> addReserveTable(@Valid @RequestBody ReservationTable table, BindingResult binding) {
//        if (binding.hasErrors()) {
//            return ResponseEntity.badRequest().body(Map.of("message", "Reservation placement failed due to validation errors"));
//        }
//        return ResponseEntity.ok(Map.of(
//                "message", "Reservation placed successfully",
//                "data", reservationService.saveReservation(table)
//        ));
//    }
//
//}
