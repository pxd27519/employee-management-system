package com.pooja.ems.service;

import com.pooja.ems.dto.AuthDto;
import com.pooja.ems.model.Role;
import com.pooja.ems.model.User;
import com.pooja.ems.repository.UserRepository;
import com.pooja.ems.security.jwt.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;
    private final AuthenticationManager authenticationManager;

    public AuthDto.LoginResponse login(AuthDto.LoginRequest request) {
        // Authenticate — throws exception if invalid
        authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(
                request.getUsername(), request.getPassword()));

        User user = userRepository.findByUsername(request.getUsername())
                .orElseThrow();

        String token = jwtUtil.generateToken(user);

        return AuthDto.LoginResponse.builder()
                .token(token)
                .username(user.getUsername())
                .role(user.getRole().name())
                .fullName(user.getFullName())
                .build();
    }

    public String register(AuthDto.RegisterRequest request) {
        if (userRepository.existsByUsername(request.getUsername())) {
            throw new RuntimeException("Username already exists");
        }

        User user = User.builder()
                .username(request.getUsername())
                .password(passwordEncoder.encode(request.getPassword()))
                .fullName(request.getFullName())
                .email(request.getEmail())
                .role(request.getRole() != null ? request.getRole() : Role.EMPLOYEE)
                .build();

        userRepository.save(user);
        return "User registered successfully";
    }
}
