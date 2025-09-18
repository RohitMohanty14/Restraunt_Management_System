//package com.cts.rms.restcontroller;
//
//import java.util.Map;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.ResponseEntity;
////import org.springframework.validation.BindingResult;
//import org.springframework.web.bind.annotation.*;
//
//import com.cts.rms.model.Menu;
//import com.cts.rms.service.MenuService;
//
////import jakarta.validation.Valid;
//
//@RestController
//@RequestMapping("/User/Menu")
//public class MenuController {
//
//	//all get method needed
//	
//	
//	
//    @Autowired
//    private MenuService menuService;
//    
//	@GetMapping("/listAll")
//    public ResponseEntity<?> listAllMenu() {
//	    return ResponseEntity.ok(Map.of(
//	                "message", "Menu retrieved successfully",
//	                "menu", menuService.getAllMenu()
//	        ));
//	    }
//	@GetMapping("/getById/{id}")
//    public ResponseEntity<?> listById(@PathVariable Long id) {
//        return ResponseEntity.ok(Map.of(
//            "message", "Menu retrieved successfully",
//            "users", menuService.getById(id)
//        ));
//    }
//	
//
//}
