package com.example.customersmanagement.controller;

import com.example.customersmanagement.entity.User;
import com.example.customersmanagement.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/profile")
    public ResponseEntity<Map<String, Object>> profile(Authentication authentication) {
        Map<String, Object> response = new HashMap<>();
        response.put("user", userService.getUserByUsername(authentication.getName()));
        return ResponseEntity.ok(response);
    }

    @GetMapping("/profile/edit")
    public ResponseEntity<Map<String, Object>> editProfileForm(Authentication authentication) {
        Map<String, Object> response = new HashMap<>();
        response.put("user", userService.getUserByUsername(authentication.getName()));
        return ResponseEntity.ok(response);
    }

    @PostMapping("/profile/edit")
    public ResponseEntity<Map<String, String>> editProfile(Authentication authentication,
                                                           @RequestParam String email) {
        userService.updateUserProfile(authentication.getName(), email);
        Map<String, String> response = new HashMap<>();
        response.put("message", "Profile updated successfully");
        response.put("status", "success");
        return ResponseEntity.ok(response);
    }

    @GetMapping("/dashboard")
    public ResponseEntity<Map<String, Object>> userDashboard(Authentication authentication) {
        Map<String, Object> response = new HashMap<>();
        response.put("user", userService.getUserByUsername(authentication.getName()));
        response.put("message", "Welcome to your dashboard");
        return ResponseEntity.ok(response);
    }

    @GetMapping("/settings")
    public ResponseEntity<Map<String, String>> settings() {
        Map<String, String> response = new HashMap<>();
        response.put("message", "User settings");
        response.put("status", "available");
        return ResponseEntity.ok(response);
    }

    @GetMapping("/activity")
    public ResponseEntity<Map<String, String>> activity() {
        Map<String, String> response = new HashMap<>();
        response.put("message", "User activity log");
        response.put("status", "available");
        return ResponseEntity.ok(response);
    }
}