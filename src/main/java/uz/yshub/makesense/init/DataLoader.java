package uz.yshub.makesense.init;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import uz.yshub.makesense.controller.AuthController;
import uz.yshub.makesense.domain.Role;
import uz.yshub.makesense.domain.User;
import uz.yshub.makesense.domain.enumeration.ERole;
import uz.yshub.makesense.repository.RoleRepository;
import uz.yshub.makesense.repository.UserRepository;

import java.util.HashSet;
import java.util.Set;

@Component
public class DataLoader implements CommandLineRunner {

    @Autowired
    UserRepository userRepository;
    @Autowired
    RoleRepository roleRepository;
    @Autowired
    PasswordEncoder passwordEncoder;
    @Autowired
    AuthController authController;

    @Override
    public void run(String... args) throws Exception {

        if (roleRepository.count() == 0) {
            roleRepository.save(new Role(ERole.ROLE_USER));
            roleRepository.save(new Role(ERole.ROLE_ADMIN));
            roleRepository.save(new Role(ERole.ROLE_MODERATOR));
        }

        if (userRepository.count() == 0) {
            Set<Role> roles = new HashSet<Role>(roleRepository.findAll());
            userRepository.save(new User("admin", "admin@mail.ru", passwordEncoder.encode("admin"), roles));
        }
    }
}
