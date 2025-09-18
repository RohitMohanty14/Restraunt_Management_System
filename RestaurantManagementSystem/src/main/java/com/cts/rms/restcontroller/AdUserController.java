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
//import org.springframework.web.bind.annotation.PutMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//import com.cts.rms.model.User;
//import com.cts.rms.service.UserService;
//
//import jakarta.validation.Valid;
//
//
//@RestController
//@RequestMapping("/Admin/User")
//public class AdUserController {
////all crud operation
//	
//	 @Autowired
//	    private UserService userService;
//	 
//	 @GetMapping("/getById/{id}")
//	    public ResponseEntity<?> listById(@PathVariable Long id) {
//	        return ResponseEntity.ok(Map.of(
//	            "message", "User retrieved successfully",
//	            "users", userService.getById(id)
//	        ));
//	    }
//	 
//	@GetMapping("/getAll")
//    public ResponseEntity<?> listAllUsers() {
//        return ResponseEntity.ok(Map.of(
//            "message", "Users retrieved successfully",
//            "users", userService.listAllUsers()
//        ));
//    }
//	
//	@DeleteMapping("/deleteUser/{id}")
//    public ResponseEntity<?> deleteUser(@PathVariable Long id) {
//        return ResponseEntity.ok(Map.of(
//            "message", "User deleted successfully",
//            "user", userService.listAllUsers().stream()
//                        .filter(u -> u.getUserId() == id)
//                        .findFirst(),
//            "value", userService.removeUser(id)
//        ));
//    }
//
// 
//    @PutMapping("/updateUser/{id}")
//    public ResponseEntity<?> updateUser(@RequestBody @Valid User user, @PathVariable Long id, BindingResult binding) {
//        if (binding.hasErrors()) {
//            return ResponseEntity.badRequest().body(Map.of("message", "User update failed"));
//        }
//        return ResponseEntity.ok(Map.of(
//            "message", "User updated successfully",
//            "user", userService.updateUser(user, id)
//        ));
//    }
//}
