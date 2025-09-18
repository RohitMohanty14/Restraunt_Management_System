//package com.cts.rms.restcontroller;
//
//import java.util.Map;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.ResponseEntity;
//import org.springframework.validation.BindingResult;
//import org.springframework.web.bind.annotation.DeleteMapping;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.PutMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//import com.cts.rms.model.ReservationTable;
//import com.cts.rms.service.TableReservationService;
//
//import jakarta.validation.Valid;
//@RestController
//@RequestMapping("/Admin/reservations")
//public class AdTableController {
//	//all crud operation
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
//    @GetMapping("/getAll")
//    public ResponseEntity<?> listAllReservations() {
//        return ResponseEntity.ok(Map.of(
//                "message", "Reservations retrieved successfully",
//                "data", reservationService.getAllReservations()
//        ));
//    }
//
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
//    @DeleteMapping("/delete/{id}")
//    public ResponseEntity<?> deleteReserveTable(@PathVariable Long id) {
//    	return ResponseEntity.ok(Map.of(
//				"message" , "Reservation Deleted Successfully",
//				"order", reservationService.getAllReservations().stream()
//                .filter(table -> table.getId()  == id)
//                .findFirst(),
//				"value", reservationService.deleteReservation(id)
//				));
//    }
//
//   
//    @PutMapping("/update/{id}")
//    public ResponseEntity<?> updateReservation(@Valid @RequestBody ReservationTable table, @PathVariable Long id, BindingResult binding) {
//        if (binding.hasErrors()) {
//            return ResponseEntity.badRequest().body(Map.of("message", "Reservation updating failed due to validation errors"));
//        }
//        return ResponseEntity.ok(Map.of(
//                "message", "Reservation Updated Successfully",
//                "data", reservationService.updateReservation(table, id)
//        ));
//    }
//}
