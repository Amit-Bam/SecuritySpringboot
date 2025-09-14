package customersmanagement.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class PublicController {

    @GetMapping("/")
    public ResponseEntity<?> home() {
        return ResponseEntity.ok(Map.of(
            "message", "Welcome to Customer Management System",
            "description", "This is a Spring Security role-based application",
            "endpoints", Map.of(
                "login", "/login",
                "admin", "/admin/dashboard",
                "user", "/user/dashboard",
                "about", "/about",
                "contact", "/contact"
            )
        ));
    }

    @GetMapping("/about")
    public ResponseEntity<?> about() {
        return ResponseEntity.ok(Map.of(
            "title", "About Customer Management System",
            "description", "A comprehensive role-based security system built with Spring Boot",
            "features", new String[]{
                "User Authentication",
                "Role-based Authorization", 
                "Admin Dashboard",
                "User Profile Management"
            }
        ));
    }

    @GetMapping("/contact")
    public ResponseEntity<?> contact() {
        return ResponseEntity.ok(Map.of(
            "title", "Contact Information",
            "email", "admin@customermanagement.com",
            "phone", "+1-234-567-8900",
            "address", "123 Security Street, Spring City"
        ));
    }
}