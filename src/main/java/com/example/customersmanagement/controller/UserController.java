package com.example.customersmanagement.controller;

import com.example.customersmanagement.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/dashboard")
    public String userDashboard(Authentication auth, Model model) {
        model.addAttribute("user", userService.getUserByUsername(auth.getName()));
        return "user/dashboard";
    }

    @GetMapping("/profile")
    public String profile(Authentication auth, Model model) {
        model.addAttribute("user", userService.getUserByUsername(auth.getName()));
        return "user/profile";
    }

    @GetMapping("/profile/edit")
    public String editProfile(Authentication auth, Model model) {
        model.addAttribute("user", userService.getUserByUsername(auth.getName()));
        return "user/edit-profile";
    }

    @GetMapping("/settings")
    public String settings() {
        return "user/settings";
    }

    @GetMapping("/activity")
    public String activity() {
        return "user/activity";
    }
}
