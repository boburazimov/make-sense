package uz.yshub.makesense.service.impl;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uz.yshub.makesense.domain.Role;
import uz.yshub.makesense.domain.User;
import uz.yshub.makesense.payload.LoginRequest;
import uz.yshub.makesense.payload.MessageResponse;
import uz.yshub.makesense.payload.SignupRequest;
import uz.yshub.makesense.repository.RoleRepository;
import uz.yshub.makesense.repository.UserRepository;
import uz.yshub.makesense.security.SecurityUtils;
import uz.yshub.makesense.security.UserDetailsImpl;
import uz.yshub.makesense.security.UserDetailsServiceImpl;
import uz.yshub.makesense.security.jwt.AuthoritiesConstants;
import uz.yshub.makesense.security.jwt.JwtUtils;
import uz.yshub.makesense.service.UserService;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final Logger log = LoggerFactory.getLogger(UserDetailsServiceImpl.class);

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;
    private final AuthenticationManager authenticationManager;
    private final JwtUtils jwtUtils;

    @Override
    public User createUser(SignupRequest request) {

        User user = new User();
        user.setUsername(request.getUsername().toLowerCase());
        if (request.getEmail() != null)
            user.setEmail(request.getEmail().toLowerCase());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        if (request.getRole() != null) {
            Set<Role> roles = request.getRole().stream()
                    .map(roleRepository::findOneByName)
                    .filter(Optional::isPresent)
                    .map(Optional::get)
                    .collect(Collectors.toSet());
        } else {
            Set<Role> roles = new HashSet<>();
            roleRepository.findOneByName(AuthoritiesConstants.USER).ifPresent(roles::add);
            user.setRoles(roles);
        }
        userRepository.save(user);
        log.debug("Created Information for User: {}", user);
        return user;
    }

    @Override
    public MessageResponse signin(LoginRequest loginRequest) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        List<String> roles = userDetails.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());
        log.debug("Sign-in Information for User: {}", userDetails);
        return new MessageResponse(true, jwt, roles);
    }

    @Override
    public Optional<User> getUserWithAuthorities() {
        return SecurityUtils.getCurrentUserLogin().flatMap(userRepository::findOneWithAuthoritiesByUsername);
    }
}
