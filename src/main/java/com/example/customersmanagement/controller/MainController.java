package com.example.customersmanagement.controller;

    import org.springframework.security.core.Authentication;
    import org.springframework.security.core.GrantedAuthority;
    import org.springframework.stereotype.Controller;
    import org.springframework.ui.Model;
    import org.springframework.web.bind.annotation.GetMapping;

    @Controller
    public class MainController {

        @GetMapping("/dashboard")
        public String dashboard(Authentication authentication, Model model) {
            // Check user role and redirect accordingly
            boolean isAdmin = authentication.getAuthorities().stream()
                    .map(GrantedAuthority::getAuthority)
                    .anyMatch(role -> role.equals("ROLE_ADMIN"));

            if (isAdmin) {
                return "redirect:/admin/dashboard";
            } else {
                return "redirect:/user/dashboard";
            }
        }

        @GetMapping("/admin/dashboard")
        public String adminDashboard(Model model) {
            model.addAttribute("message", "Welcome to Admin Dashboard");
            return "admin/dashboard";
        }

        @GetMapping("/user/dashboard")
        public String userDashboard(Model model) {
            model.addAttribute("message", "Welcome to User Dashboard");
            return "user/dashboard";
        }

        @GetMapping("/access-denied")
        public String accessDenied(Model model) {
            model.addAttribute("error", "Access Denied - You don't have permission to access this page");
            return "access-denied";
        }

        @GetMapping("/")
        public String home() {
            return "index";
        }
    }