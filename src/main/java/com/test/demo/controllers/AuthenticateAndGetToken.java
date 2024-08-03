package com.test.demo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import com.test.demo.config.JwtService;
import com.test.demo.dto.AuthRequestDto;
import com.test.demo.dto.JwtResponseDto;

@RestController
@RequestMapping("/api/v1")
public class AuthenticateAndGetToken {

    @Autowired
    private JwtService jwtService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @PostMapping("login")
    public JwtResponseDto authenticateAndGetToken(@RequestBody AuthRequestDto
     authRequestDTO) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(authRequestDTO.getUsername(),
                 authRequestDTO.getPassword()));
        if (authentication.isAuthenticated()) {
            return JwtResponseDto.builder()
                    .accessToken(jwtService.GenerateToken(authRequestDTO.getUsername()))
                    .build();
        } else {
            throw new UsernameNotFoundException("invalid user request..!!");
        }
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/admin")
    public String test() {
        try {
            return "Welcome Admin";
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @GetMapping("/user")
    public String testUser() {
        try {
            return "Welcome User";
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
