package customersmanagement.controller;

import org.springframework.http.ResponseEntity;
import com.example.customersmanagement.entity.Role;
import com.example.customersmanagement.entity.User;
import customersmanagement.repository.RoleRepository;
import customersmanagement.repository.UserRepository;
import com.example.customersmanagement.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/admin")
public class AdminController {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final UserService userService;

    public AdminController(UserRepository userRepository, RoleRepository roleRepository, UserService userService) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.userService = userService;
    }

    @GetMapping("/dashboard")
    public ResponseEntity<?> dashboard() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Map<String, Object> dashboard = new HashMap<>();
        dashboard.put("message", "Welcome to Admin Dashboard");
        dashboard.put("user", auth.getName());
        dashboard.put("totalUsers", userRepository.count());
        dashboard.put("totalRoles", roleRepository.count());
        return ResponseEntity.ok(dashboard);
    }

    @GetMapping("/users")
    public ResponseEntity<?> getAllUsers() {
        List<User> users = userRepository.findAllUsersWithRoles(); // Use the custom query method
        List<Map<String, Object>> userList = users.stream().map(user -> {
            Map<String, Object> userMap = new HashMap<>();
            userMap.put("id", user.getId());
            userMap.put("username", user.getUsername());
            userMap.put("email", user.getEmail());
            userMap.put("enabled", user.isEnabled());
            userMap.put("roles", user.getRoles().stream()
                    .map(Role::getRoleName)
                    .collect(Collectors.toList()));
            return userMap;
        }).collect(Collectors.toList());
        
        return ResponseEntity.ok(Map.of("users", userList));
    }

    @PostMapping("/users/create")
    public ResponseEntity<?> createUser(@RequestBody Map<String, Object> userData) {
        try {
            String username = (String) userData.get("username");
            String password = (String) userData.get("password");
            String email = (String) userData.get("email");
            
            if (userRepository.existsByUsername(username)) {
                return ResponseEntity.badRequest().body(Map.of("error", "Username already exists"));
            }

            User newUser = userService.createUser(username, password, email);
            return ResponseEntity.ok(Map.of("message", "User created successfully", "userId", newUser.getId()));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("error", "Failed to create user: " + e.getMessage()));
        }
    }

    @GetMapping("/users/{id}")
    public ResponseEntity<?> getUser(@PathVariable Long id) {
        return userRepository.findByIdWithRoles(id)
                .map(user -> {
                    Map<String, Object> userMap = new HashMap<>();
                    userMap.put("id", user.getId());
                    userMap.put("username", user.getUsername());
                    userMap.put("email", user.getEmail());
                    userMap.put("enabled", user.isEnabled());
                    userMap.put("roles", user.getRoles().stream()
                            .map(Role::getRoleName)
                            .collect(Collectors.toList()));
                    return ResponseEntity.ok(userMap);
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/users/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable Long id) {
        if (!userRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        userRepository.deleteById(id);
        return ResponseEntity.ok(Map.of("message", "User deleted successfully"));
    }

    @GetMapping("/roles")
    public ResponseEntity<?> getAllRoles() {
        List<Role> roles = roleRepository.findAll();
        List<Map<String, Object>> roleList = roles.stream().map(role -> {
            Map<String, Object> roleMap = new HashMap<>();
            roleMap.put("id", role.getId());
            roleMap.put("roleName", role.getRoleName());
            roleMap.put("description", role.getDescription());
            roleMap.put("userCount", role.getUsers().size());
            return roleMap;
        }).collect(Collectors.toList());
        
        return ResponseEntity.ok(Map.of("roles", roleList));
    }

    @GetMapping("/reports")
    public ResponseEntity<?> getReports() {
        Map<String, Object> reports = new HashMap<>();
        reports.put("totalUsers", userRepository.count());
        reports.put("totalRoles", roleRepository.count());
        reports.put("enabledUsers", userRepository.findAll().stream()
                .mapToLong(user -> user.isEnabled() ? 1 : 0).sum());
        
        return ResponseEntity.ok(reports);
    }
}