package com.example.customersmanagement.controller;

            import org.springframework.http.ResponseEntity;
            import org.springframework.web.bind.annotation.*;

            import java.util.HashMap;
            import java.util.Map;

            @RestController
            public class MainController {

                @GetMapping("/")
                public ResponseEntity<Map<String, String>> home() {
                    Map<String, String> response = new HashMap<>();
                    response.put("message", "Welcome to Customer Management API");
                    response.put("status", "success");
                    return ResponseEntity.ok(response);
                }

                @GetMapping("/about")
                public ResponseEntity<Map<String, String>> about() {
                    Map<String, String> response = new HashMap<>();
                    response.put("message", "About Customer Management System");
                    response.put("version", "1.0.0");
                    return ResponseEntity.ok(response);
                }

                @GetMapping("/contact")
                public ResponseEntity<Map<String, String>> contact() {
                    Map<String, String> response = new HashMap<>();
                    response.put("message", "Contact information");
                    response.put("email", "support@example.com");
                    return ResponseEntity.ok(response);
                }
            }