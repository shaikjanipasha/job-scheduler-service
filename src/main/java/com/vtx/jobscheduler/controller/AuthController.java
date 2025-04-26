package com.vtx.jobscheduler.controller;

import com.vtx.jobscheduler.model.*;
import com.vtx.jobscheduler.service.JwtService;
import com.vtx.jobscheduler.service.impl.UserServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final UserServiceImpl userService;
    private final BCryptPasswordEncoder passwordEncoder;

    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@RequestBody RegistrationRequestContract registrationRequest) {
        UserDetailsDto responseDto = userService.getUserDetailsByUserName(registrationRequest.getUsername());
        if (responseDto != null) {
            throw new IllegalArgumentException("User is already present in the system. Please login. ");
        }

        // Encrypt the password
        String encryptedPassword = passwordEncoder.encode(registrationRequest.getPassword());
        registrationRequest.setPassword(encryptedPassword);
        userService.saveUser(registrationRequest);
        return ResponseEntity.ok("User registered successfully.");
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginRequest.username(), loginRequest.password())
            );
        log.info("Pull User Information from DB /login");
        UserDetailsDto user = userService.getUserDetailsByUserName(loginRequest.username());

        if (user == null) {
           throw  new UsernameNotFoundException("User not found");
        }

        String token = jwtService.generateToken(user.getUsername(), user.getRole());
        return ResponseEntity.ok(new JwtResponse(token));
    }
}
