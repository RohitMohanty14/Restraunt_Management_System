//package com.cts.rms.restcontroller;
//
//import java.util.Map;
//
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
//import com.cts.rms.model.Orders;
//import com.cts.rms.service.OrderService;
//
//import jakarta.validation.Valid;
//
//@RestController
//@RequestMapping("/User/Order")
//public class OrderController {
//   
//	//all get method needed
//	
//	@Autowired
//	private OrderService orderService;
//	
//	
//	@PostMapping("/placeOrder")
//	public ResponseEntity<?> placeOrder(@RequestBody @Valid Orders order , BindingResult binding) {
//	    if (binding.hasErrors()) {
//	        return ResponseEntity.badRequest().body(Map.of("message", "Order placement failed"));
//	    }
//	    return ResponseEntity.ok(Map.of(
//	        "message", "Order placed successfully",
//	        "order", orderService.saveOrder(order)
//	    ));
//	}
//	@GetMapping("/getAll")
//	public ResponseEntity<?>  listAllOrder(){
//	    return ResponseEntity.ok(Map.of(
//	        "message", "Order retreived successfully",
//	        "order", orderService.getAllOrder()
//	    ));
//	}
//	@PutMapping("/updateOrder/{id}")
//	public ResponseEntity<?> updateOrder(@RequestBody @Valid Orders order , @PathVariable Long id , BindingResult binding) {
//		if(binding.hasErrors()) {
//			return ResponseEntity.badRequest().body(Map.of("message", "Order updating failed"));
//		}
//		return ResponseEntity.ok(Map.of(
//				"message" , "Order Updated Successfully",
//				"order", orderService.updateOrder(order, id)
//				));
//	}
//	@DeleteMapping("/deleteOrder/{id}")
//	public ResponseEntity<?>  cancelOrder(@PathVariable Long id ) {
//		return ResponseEntity.ok(Map.of(
//				"message" , "Order Deleted Successfully",
//				"order", orderService.getAllOrder().stream()
//                .filter(n -> n.getOrderId() == id)
//                .findFirst(),
//				"value", orderService.deleteOrder(id)
//				));
//	}
//	
//}
