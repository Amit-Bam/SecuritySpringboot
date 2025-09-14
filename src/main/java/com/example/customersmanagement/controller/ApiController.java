package com.example.customersmanagement.controller;

        import org.springframework.beans.factory.annotation.Autowired;
        import org.springframework.http.ResponseEntity;
        import org.springframework.security.authentication.AuthenticationManager;
        import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
        import org.springframework.security.core.Authentication;
        import org.springframework.security.core.AuthenticationException;
        import org.springframework.web.bind.annotation.*;

        import java.util.HashMap;
        import java.util.Map;

        @RestController
        @RequestMapping("/api")
        public class ApiController {

            @Autowired
            private AuthenticationManager authenticationManager;

            @GetMapping("/public/health")
            public ResponseEntity<Map<String, String>> health() {
                Map<String, String> response = new HashMap<>();
                response.put("status", "OK");
                response.put("message", "Public endpoint accessible");
                return ResponseEntity.ok(response);
            }

            @PostMapping("/public/login")
            public ResponseEntity<Map<String, String>> login(
                    @RequestParam String username,
                    @RequestParam String password) {

                try {
                    Authentication auth = authenticationManager.authenticate(
                            new UsernamePasswordAuthenticationToken(username, password)
                    );

                    Map<String, String> response = new HashMap<>();
                    response.put("message", "Login successful");
                    response.put("user", auth.getName());
                    response.put("authorities", auth.getAuthorities().toString());
                    return ResponseEntity.ok(response);
                } catch (AuthenticationException e) {
                    Map<String, String> response = new HashMap<>();
                    response.put("error", "Invalid credentials");
                    return ResponseEntity.status(401).body(response);
                }
            }

            @GetMapping("/admin/dashboard")
            public ResponseEntity<Map<String, Object>> adminDashboard(Authentication authentication) {
                Map<String, Object> response = new HashMap<>();
                response.put("message", "Welcome to Admin Dashboard");
                response.put("user", authentication.getName());
                response.put("roles", authentication.getAuthorities());
                return ResponseEntity.ok(response);
            }

            @GetMapping("/user/profile")
            public ResponseEntity<Map<String, Object>> userProfile(Authentication authentication) {
                Map<String, Object> response = new HashMap<>();
                response.put("message", "User Profile");
                response.put("user", authentication.getName());
                response.put("roles", authentication.getAuthorities());
                return ResponseEntity.ok(response);
            }
        }