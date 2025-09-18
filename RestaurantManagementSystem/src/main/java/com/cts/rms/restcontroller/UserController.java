//package com.cts.rms.restcontroller;
//
//import java.util.Map;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.ResponseEntity;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
//import org.springframework.validation.BindingResult;
//import org.springframework.web.bind.annotation.*;
//
//import com.cts.rms.model.User;
//import com.cts.rms.config.CustomUserDetailsService;
//import com.cts.rms.config.JwtUtil;
//import com.cts.rms.service.UserService;
//
//import jakarta.validation.Valid;
//
//@RestController
//@RequestMapping("/User")
//public class UserController {
//	//all get method needed
//	
//	
//    @Autowired
//    private UserService userService;
//    
//    @Autowired
//    private AuthenticationManager authenticationManager;
//
//    @Autowired
//    private JwtUtil jwtUtil;
//
//    @Autowired
//    private CustomUserDetailsService userDetailsService;
//    
//    @GetMapping("/getById/{id}")
//    public ResponseEntity<?> listById(@PathVariable Long id) {
//        return ResponseEntity.ok(Map.of(
//            "message", "User retrieved successfully",
//            "users", userService.getById(id)
//        ));
//    }
//    
//    @PostMapping("/register")
//    public ResponseEntity<?> registerUser(@RequestBody @Valid User user, BindingResult binding) {
//        if (binding.hasErrors()) {
//            return ResponseEntity.badRequest().body(Map.of("message", "User registration failed"));
//        }
//        return ResponseEntity.ok(Map.of(
//            "message", "User registered successfully",
//            "user", userService.addUser(user)
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
//    @PostMapping("/login")
//    public ResponseEntity<?> login(@RequestBody Map<String, String> request) {
//        try {
//            String email = request.get("email");
//            String password = request.get("password");
//
//            // Step 1: Check if user exists
//            UserDetails userDetails;
//            try {
//                userDetails = userDetailsService.loadUserByUsername(email);
//            } catch (UsernameNotFoundException ex) {
//                return ResponseEntity.status(404).body(Map.of("message", ex.getMessage()));
//            }
//
//            // Step 2: Authenticate password
//            authenticationManager.authenticate(
//                new UsernamePasswordAuthenticationToken(email, password)
//            );
//
//            // Step 3: Generate token if successful
//            String token = jwtUtil.generateToken(
//                userDetails.getUsername(),
//                userDetails.getAuthorities().iterator().next().getAuthority()
//            );
//
//            return ResponseEntity.ok(Map.of(
//                "message", "Login successful",
//                "token", token
//            ));
//
//        } catch (org.springframework.security.authentication.BadCredentialsException ex) {
//            return ResponseEntity.status(401).body(Map.of("message", "Invalid password"));
//        } catch (Exception e) {
//            return ResponseEntity.badRequest().body(Map.of("message", "Invalid credentials"));
//        }
//    }
//
//}
