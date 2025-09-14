package com.example.customersmanagement;

        import com.example.customersmanagement.service.RoleService;
        import com.example.customersmanagement.service.UserService;
        import lombok.RequiredArgsConstructor;
        import org.springframework.boot.CommandLineRunner;
        import org.springframework.stereotype.Component;

        import java.util.Set;

        @Component
        @RequiredArgsConstructor
        public class DataInitializer implements CommandLineRunner {

            private final RoleService roleService;
            private final UserService userService;

            @Override
            public void run(String... args) {
                // Create default roles
                roleService.createDefaultRoles();

                // Create default admin user if not exists
                if (!userService.existsByUsername("admin")) {
                    userService.createUser("admin", "admin123", "admin@example.com", Set.of("ADMIN"));
                }

                // Create default regular user if not exists
                if (!userService.existsByUsername("user")) {
                    userService.createUser("user", "user123", "user@example.com", Set.of("USER"));
                }
            }
        }