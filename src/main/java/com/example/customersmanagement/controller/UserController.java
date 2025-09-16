package com.example.customersmanagement.controller;
                import org.springframework.http.ResponseEntity;
                import org.springframework.security.core.Authentication;
                import org.springframework.security.core.context.SecurityContextHolder;
                import org.springframework.web.bind.annotation.*;

                import java.util.HashMap;
                import java.util.Map;

                @RestController
                @RequestMapping("/user")
                public class UserController {
                    @GetMapping("/dashboard")
                    public ResponseEntity<?> dashboard() {
                        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
                        Map<String, Object> dashboard = new HashMap<>();
                        dashboard.put("message", "Welcome to User Dashboard");
                        dashboard.put("user", auth.getName());
                        return ResponseEntity.ok(dashboard);
                    }
}