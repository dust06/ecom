package com.ecom.authservice.auth.controllers;

import com.ecom.authservice.auth.dtos.requestDTOs.LoginRequest;
import com.ecom.authservice.auth.dtos.requestDTOs.UserNamePasswordSingUp;
import com.ecom.authservice.auth.dtos.responseDTOs.SignUpResponseDTO;
import com.ecom.authservice.auth.dtos.responseDTOs.LoginResponseDTO;
import com.ecom.authservice.auth.service.AuthService;
import com.ecom.authservice.user.entities.User;
import com.ecom.authservice.user.service.UserService;
import com.ecom.authservice.security.JwtTokenProvider;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final UserService userService;
    private final AuthService authService;
    private final JwtTokenProvider jwtTokenProvider;


    public AuthController(AuthenticationManager authenticationManager, UserService userService, AuthService authService, JwtTokenProvider jwtTokenProvider) {
        this.authenticationManager = authenticationManager;
        this.userService = userService;
        this.authService = authService;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(@Valid @RequestBody LoginRequest body) throws AuthenticationException {
        String userName = body.username();
        String password = body.password();
        var authentication = new UsernamePasswordAuthenticationToken(userName, password);
        Authentication auth = authenticationManager.authenticate(authentication);
        UserDetails userDetails = (UserDetails) auth.getPrincipal();
        String token = jwtTokenProvider.generateToken(userDetails.getUsername());
        return ResponseEntity.ok(new LoginResponseDTO(token));
    }

    @PostMapping("/signUp")
    public ResponseEntity<SignUpResponseDTO> signUp (@Valid @RequestBody UserNamePasswordSingUp body){

        String username = body.username();
        String password = body.password();
        String bio = body.bio();

        User user = userService.registerUser(username, password, bio);
        String token = jwtTokenProvider.generateToken(user.getUsername());
        return ResponseEntity.ok(new SignUpResponseDTO(token));
    }

}
