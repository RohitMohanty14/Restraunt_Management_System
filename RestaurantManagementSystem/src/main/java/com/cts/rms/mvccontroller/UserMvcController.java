package com.cts.rms.mvccontroller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import com.cts.rms.model.User;
import com.cts.rms.config.CustomUserDetailsService;
import com.cts.rms.config.JwtUtil;
import com.cts.rms.service.UserService;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;

@Controller
@RequestMapping("/User")
public class UserMvcController {

    @Autowired
    private UserService userService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private CustomUserDetailsService userDetailsService;



    @GetMapping("/register")
    public String showRegisterForm(Model model) {
        model.addAttribute("user", new User());
        return "Register";
    }

    @PostMapping("/register")
    public String registerUser(@ModelAttribute("user") @Valid User user,
                               BindingResult binding, Model model) {
        if (binding.hasErrors()) {
            model.addAttribute("message", "User registration failed");
            return "Register";
        }
        if (userService.findByEmail(user.getEmail()).isPresent()) {
            model.addAttribute("message", "Email already exists! Please login.");
            return "Login"; 
        }
        user.setRole("USER");
        userService.addUser(user);
        model.addAttribute("message", "User registered successfully. Please login.");
        return "Login";
    }

    @GetMapping("/login")
    public String showLoginForm(Model model) {
        model.addAttribute("user", new User()); 
        return "Login";
    }


    @PostMapping("/login")
    public String login(@ModelAttribute("user") User user, Model model, HttpServletResponse response) {
        String email = user.getEmail();
        String password = user.getPassword();

        try {
            UserDetails userDetails;
            try {
                userDetails = userDetailsService.loadUserByUsername(email);
            } catch (UsernameNotFoundException ex) {
                model.addAttribute("message", ex.getMessage());
                return "Login";
            }

            authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(email, password)
            );

            String role = userDetails.getAuthorities().iterator().next().getAuthority();
            String token = jwtUtil.generateToken(userDetails.getUsername(), role);
           System.out.println(role);
            Cookie jwtCookie = new Cookie("jwt", token);
            jwtCookie.setHttpOnly(true);
            jwtCookie.setPath("/");
            jwtCookie.setMaxAge(60 * 60); // 1 hour
            response.addCookie(jwtCookie);

            if ("ROLE_ADMIN".equals(role)) {
                return "redirect:/Admin/dashboard";
            } else {
                return "redirect:/User/Menu/Home";
            }

        } catch (org.springframework.security.authentication.BadCredentialsException ex) {
            model.addAttribute("message", "Invalid password");
            return "Login";
        } catch (Exception e) {
            model.addAttribute("message", "Invalid credentials");
            return "Login";
        }
    }
}