
package nikhilsable.auth.service.service;

import nikhilsable.auth.service.dto.LoginRequest;
import nikhilsable.auth.service.dto.LoginResponse;
import nikhilsable.auth.service.dto.RegisterRequest;
import nikhilsable.auth.service.dto.RegisterResponse;
import nikhilsable.auth.service.entity.User;
import nikhilsable.auth.service.repository.UserRepository;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AuthService {

    private final JwtService jwtService;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final RefreshTokenService refreshTokenService;

    public AuthService(
            UserRepository userRepository,
            PasswordEncoder passwordEncoder,
            JwtService jwtService,
            RefreshTokenService refreshTokenService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
        this.refreshTokenService = refreshTokenService;
    }

    @Transactional
    public RegisterResponse register(RegisterRequest request) {

        if (userRepository.existsByUsername(request.getUsername())) {
            throw new IllegalArgumentException("Username already exists");
        }

        if (userRepository.existsByEmail(request.getEmail())) {
            throw new IllegalArgumentException("Email already exists");
        }

        User user = new User();
        user.setUsername(request.getUsername());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));

        User savedUser = userRepository.save(user);

        return new RegisterResponse(
                savedUser.getId(),
                savedUser.getUsername(),
                savedUser.getEmail(),
                "User registered successfully"
        );
    }

    @Transactional
    public LoginResponse login(LoginRequest request) {

        User user = userRepository
                .findByUsername(request.getUsername())
                .orElseThrow(() ->
                        new BadCredentialsException(
                                "Invalid username or password"));

        if (!passwordEncoder.matches(
                request.getPassword(),
                user.getPassword())) {
            throw new BadCredentialsException(
                    "Invalid username or password");
        }

        String accessToken =
                jwtService.generateToken(user.getUsername());

        String refreshToken =
                refreshTokenService.createRefreshToken(user);

        return new LoginResponse(
                accessToken,
                refreshToken,
                "Bearer",
                900
        );
    }

    @Transactional
    public LoginResponse refresh(String rawRefreshToken) {

        User user =
                refreshTokenService.consumeRefreshToken(rawRefreshToken);

        String newAccessToken =
                jwtService.generateToken(user.getUsername());

        String newRefreshToken =
                refreshTokenService.createRefreshToken(user);

        return new LoginResponse(
                newAccessToken,
                newRefreshToken,
                "Bearer",
                900
        );
    }
}
