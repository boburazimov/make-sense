package uz.yshub.makesense.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.yshub.makesense.domain.User;
import uz.yshub.makesense.payload.LoginRequest;
import uz.yshub.makesense.payload.ApiResponse;
import uz.yshub.makesense.payload.SignupRequest;
import uz.yshub.makesense.repository.UserRepository;
import uz.yshub.makesense.security.jwt.JwtUtils;
import uz.yshub.makesense.service.UserService;
import uz.yshub.makesense.service.dto.UserDTO;

import javax.security.auth.login.AccountException;
import javax.validation.Valid;

@RequiredArgsConstructor
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/auth")
@Tag(name = "Account", description = "The Account API.")
public class AuthResource {

    private final Logger log = LoggerFactory.getLogger(AuthResource.class);

    private final UserRepository userRepository;
    private final JwtUtils jwtUtils;
    private final UserService userService;

    private static class AccountResourceException extends RuntimeException {
        private AccountResourceException(String message) {
            super(message);
        }
    }

    /**
     * {@code GET  /auth/signin} : check if the user is authenticated, and return its login.
     *
     * @param loginRequest the HTTP request.
     * @return the login if the user is authenticated.
     */
    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
        log.debug("REST request to Sign-in Account");

        ApiResponse apiResponse = userService.signin(loginRequest);
        return ResponseEntity.ok(apiResponse);
    }

    /**
     * {@code POST  /auth/signup}  : Create a new user.
     * <p>
     * Create a new user if the login and email are not already used.
     *
     * @param signUpRequest the user to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new user.
     * or with status {@code 400 (Bad Request)} if the login or email is already in use.
     * @throws {@code 400 (Bad Request)} if the login or email is already in use.
     */
    @PostMapping("/signup")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signUpRequest) {
        log.debug("REST request to register new User : {}", signUpRequest);

        if (userRepository.existsByUsernameIgnoreCase(signUpRequest.getUsername())) {
            return ResponseEntity.badRequest().body(new ApiResponse(false, "Error: Username is already taken!"));
        }
        if (userRepository.existsByEmailIgnoreCase(signUpRequest.getEmail())) {
            return ResponseEntity.badRequest().body(new ApiResponse(false, "Error: Email is already in use!"));
        }

        User newUser = userService.createUser(signUpRequest);
        return ResponseEntity.ok(new ApiResponse(true, "User registered successfully!", newUser));
    }

//    @PostMapping("/account")
//    public ResponseEntity<?> update(@Valid @RequestBody SignupRequest signUpRequest){
//
//    }

    /**
     * {@code GET  /auth/account} : get the current user.
     * <p>
     * @return the current user.
     * @throws RuntimeException {@code 500 (Internal Server Error)} if the user couldn't be returned.
     */
    @GetMapping("/account")
    public UserDTO getAccount() throws AccountException {
        return userService
                .getUserWithAuthorities()
                .map(UserDTO::new)
                .orElseThrow(() -> new AccountResourceException("User could not be found"));
    }

    /**
     * {@code POST /auth/signout} : Log out User.
     *
     * @return message with success true or false with text in compliance.
     */
    @PostMapping("/signout")
    public ResponseEntity<?> logoutUser() {
        ResponseCookie cookie = jwtUtils.getCleanJwtCookie();
        return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE, cookie.toString())
                .body(new ApiResponse(true, "You've been signed out!"));
    }
}
