package uz.yshub.makesense.service;

import org.springframework.transaction.annotation.Transactional;
import uz.yshub.makesense.domain.User;
import uz.yshub.makesense.service.dto.LoginRequest;
import uz.yshub.makesense.service.dto.ApiResponse;
import uz.yshub.makesense.service.dto.SignupRequest;

import java.util.Optional;

public interface UserService {

    /**
     * Register new User in DataBase with all privilege (Admin, Moder, User)
     * @param request DTO from request
     * @return registered new User from DB.
     */
    User createUser(SignupRequest request);

    /**
     * Check if the user is authenticated, and return its login.
     *
     * @param loginRequest the HTTP request.
     * @return the login if the user is authenticated.
     */
    ApiResponse signin(LoginRequest loginRequest);

    /**
     * Get current user with user-roles.
     *
     * @return User
     */
    @Transactional(readOnly = true)
    Optional<User> getUserWithAuthorities();
}
