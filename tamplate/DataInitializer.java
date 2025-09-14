package customersmanagement;

import com.example.customersmanagement.entity.User;
import com.example.customersmanagement.entity.Role;
import com.example.customersmanagement.repository.UserRepository;
import com.example.customersmanagement.repository.RoleRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;

@Configuration
public class DataInitializer {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    @Transactional
    public CommandLineRunner initializeUsers(
            UserRepository userRepository,
            RoleRepository roleRepository,
            PasswordEncoder passwordEncoder
    ) {
        return args -> {
            if (userRepository.count() == 0) {
                // Create ADMIN role if it doesn't exist
                Role adminRole;
                if (roleRepository.findByRoleName("ADMIN").isEmpty()) {
                    Role role = new Role();
                    role.setRoleName("ADMIN");
                    role.setDescription("System administrator");
                    adminRole = roleRepository.save(role);
                } else {
                    adminRole = roleRepository.findByRoleName("ADMIN").get();
                }

                // Create USER role if it doesn't exist
                Role userRole;
                if (roleRepository.findByRoleName("USER").isEmpty()) {
                    Role role = new Role();
                    role.setRoleName("USER");
                    role.setDescription("User in the system");
                    userRole = roleRepository.save(role);
                } else {
                    userRole = roleRepository.findByRoleName("USER").get();
                }

                // Create admin user
                User adminUser = new User();
                adminUser.setUsername("admin");
                adminUser.setPassword(passwordEncoder.encode("admin"));
                adminUser.setRoles(Collections.singleton(adminRole));
                userRepository.save(adminUser);
                System.out.println("Default admin user created: username='admin', password='admin'");

                // Create default user
                User defaultUser = new User();
                defaultUser.setUsername("user");
                defaultUser.setPassword(passwordEncoder.encode("user"));
                defaultUser.setRoles(Collections.singleton(userRole));
                userRepository.save(defaultUser);
                System.out.println("Default user created: username='user', password='user'");
            }
        };
    }
}