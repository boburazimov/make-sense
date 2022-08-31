package uz.yshub.makesense.service;

import org.springframework.transaction.annotation.Transactional;
import uz.yshub.makesense.domain.User;
import uz.yshub.makesense.payload.LoginRequest;
import uz.yshub.makesense.payload.MessageResponse;
import uz.yshub.makesense.payload.SignupRequest;

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
    MessageResponse signin(LoginRequest loginRequest);

    /**
     * Get current user with user-roles.
     *
     * @return User
     */
    @Transactional(readOnly = true)
    Optional<User> getUserWithAuthorities();
}
