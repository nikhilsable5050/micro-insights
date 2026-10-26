package nikhilsable.auth.service.controller;

import nikhilsable.auth.service.dto.RefreshRequest;
import org.springframework.http.HttpStatus;
import nikhilsable.auth.service.dto.LoginRequest;
import nikhilsable.auth.service.dto.LoginResponse;
import jakarta.validation.Valid;
import nikhilsable.auth.service.dto.RegisterRequest;
import nikhilsable.auth.service.dto.RegisterResponse;
import nikhilsable.auth.service.service.AuthService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public RegisterResponse register(
            @Valid @RequestBody RegisterRequest request) {
        return authService.register(request);
    }
    @PostMapping("/login")
    public LoginResponse login(
            @Valid @RequestBody LoginRequest request) {
        return authService.login(request);
    }
    @GetMapping("/test")
    public String test() {
        return "Authentication successful!";
    }
    @PostMapping("/refresh")
    public LoginResponse refresh(
            @Valid @RequestBody RefreshRequest request) {

        return authService.refresh(request.getRefreshToken());
    }
}