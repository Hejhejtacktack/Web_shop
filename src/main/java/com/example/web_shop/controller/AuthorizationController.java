package com.example.web_shop.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AuthorizationController {
    @GetMapping("/access-denied")
    public String accessDenied(Model model) {
        model.addAttribute("error", "Access denied"); // Set the error message
        return "accessDenied"; // Return the access denied page
    }

}
