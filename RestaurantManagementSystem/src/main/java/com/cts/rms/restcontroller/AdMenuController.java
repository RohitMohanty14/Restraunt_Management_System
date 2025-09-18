//package com.cts.rms.restcontroller;
//
//import java.util.Map;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.ResponseEntity;
//import org.springframework.validation.BindingResult;
//import org.springframework.web.bind.annotation.*;
//
//import com.cts.rms.model.Menu;
//import com.cts.rms.service.MenuService;
//
//import jakarta.validation.Valid;
//
//@RestController
//@RequestMapping("/Admin/Menu")
//public class AdMenuController {
//
//	//all get method needed
//	
//	
//	
//    @Autowired
//    private MenuService menuService;
//
//    @GetMapping("/getAll")
//    public ResponseEntity<?> listAllMenu() {
//        return ResponseEntity.ok(Map.of(
//                "message", "Menu retrieved successfully",
//                "menu", menuService.getAllMenu()
//        ));
//    }
//	@GetMapping("/getById/{id}")
//    public ResponseEntity<?> listById(@PathVariable Long id) {
//        return ResponseEntity.ok(Map.of(
//            "message", "Menu retrieved successfully",
//            "users", menuService.getById(id)
//        ));
//    }
//
//    @PostMapping("/addMenu")
//    public ResponseEntity<?> addMenu(@RequestBody @Valid Menu menu, BindingResult binding) {
//        if (binding.hasErrors()) {
//            return ResponseEntity.badRequest().body(Map.of("message", "Menu creation failed"));
//        }
//        return ResponseEntity.ok(Map.of(
//                "message", "Menu added successfully",
//                "menu", menuService.saveMenu(menu)
//        ));
//    }
//
//    @DeleteMapping("/deleteMenu/{id}")
//    public ResponseEntity<?> deleteMenu(@PathVariable Long id) {
//        return ResponseEntity.ok(Map.of(
//                "message", "Menu Deleted Successfully",
//                "menu", menuService.getAllMenu().stream()
//                        .filter(n -> n.getMenuId().equals(id))
//                        .findFirst(),
//                "value", menuService.deleteMenu(id)
//        ));
//    }
//
//    @PutMapping("/updateMenu/{id}")
//    public ResponseEntity<?> updateMenu(@RequestBody @Valid Menu menu, @PathVariable Long id, BindingResult binding) {
//        if (binding.hasErrors()) {
//            return ResponseEntity.badRequest().body(Map.of("message", "Menu updating failed"));
//        }
//        return ResponseEntity.ok(Map.of(
//                "message", "Menu Updated Successfully",
//                "menu", menuService.updateMenu(menu, id)
//        ));
//    }
//}
