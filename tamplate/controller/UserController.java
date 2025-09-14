package customersmanagement.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import com.example.customersmanagement.entity.User;
import customersmanagement.repository.UserRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/user")
public class UserController {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserController(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping("/dashboard")
    public ResponseEntity<?> dashboard() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Map<String, Object> dashboard = new HashMap<>();
        dashboard.put("message", "Welcome to User Dashboard");
        dashboard.put("user", auth.getName());
        dashboard.put("roles", auth.getAuthorities());
        return ResponseEntity.ok(dashboard);
    }

    @GetMapping("/profile")
    public ResponseEntity<?> getProfile() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        
        return userRepository.findWithRolesByUsername(username)
                .map(user -> {
                    Map<String, Object> profile = new HashMap<>();
                    profile.put("id", user.getId());
                    profile.put("username", user.getUsername());
                    profile.put("email", user.getEmail());
                    profile.put("roles", user.getRoles().stream()
                            .map(role -> role.getRoleName())
                            .toList());
                    return ResponseEntity.ok(profile);
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/profile/edit")
    public ResponseEntity<?> editProfile(@RequestBody Map<String, String> profileData) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();

        return userRepository.findWithRolesByUsername(username)
                .map(user -> {
                    if (profileData.containsKey("email")) {
                        user.setEmail(profileData.get("email"));
                    }
                    userRepository.save(user);
                    return ResponseEntity.ok(Map.of("message", "Profile updated successfully"));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/settings")
    public ResponseEntity<?> getSettings() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Map<String, Object> settings = new HashMap<>();
        settings.put("user", auth.getName());
        settings.put("theme", "default");
        settings.put("notifications", true);
        return ResponseEntity.ok(settings);
    }

    @GetMapping("/activity")
    public ResponseEntity<?> getActivity() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Map<String, Object> activity = new HashMap<>();
        activity.put("user", auth.getName());
        activity.put("lastLogin", "Recent");
        activity.put("actions", "Profile viewed, Settings accessed");
        return ResponseEntity.ok(activity);
    }
}