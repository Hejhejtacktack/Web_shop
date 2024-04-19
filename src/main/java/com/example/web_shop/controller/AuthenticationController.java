package com.example.web_shop.controller;


import com.example.web_shop.model.UserDTO;
import com.example.web_shop.repository.UserRepository;
import com.example.web_shop.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
public class AuthenticationController {

    private final UserDetailsManager userDetailsManager;
    private final UserService userService;
    private final UserRepository userRepo;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public AuthenticationController(UserDetailsManager userDetailsManager,
                                    UserService userService,
                                    UserRepository userRepo,
                                    PasswordEncoder passwordEncoder) {
        this.userDetailsManager = userDetailsManager;
        this.userService = userService;
        this.userRepo = userRepo;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping("/login")
    public String showLoginForm() {
        return "loginForm";
    }

    @GetMapping("/register")
    public String showRegistrationForm() {
        return "registerForm";
    }

    @PostMapping("/register")
    public String registerUser(@ModelAttribute UserDTO userDTO) {
        UserDetails user = User.builder()
                .username(userDTO.getUsername())
                .password(passwordEncoder.encode(userDTO.getPassword()))
                .roles("USER")
                .build();

        this.userDetailsManager.createUser(user);

        return "redirect:/login";
    }
}
