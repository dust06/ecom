package com.ecom.auth.controllers;

import com.ecom.auth.dtos.requestDTOs.LoginRequest;
import com.ecom.auth.dtos.requestDTOs.UserNamePasswordSingUp;
import com.ecom.auth.dtos.responseDTOs.SignUpResponseDTO;
import com.ecom.auth.dtos.responseDTOs.LoginResponseDTO;
import com.ecom.auth.service.CustomUserDetailService;
import com.ecom.auth.util.JwtUtils;
import org.springframework.beans.propertyeditors.CustomBooleanEditor;
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
    private final CustomUserDetailService customUserDetailService;
    private final JwtUtils jwtUtils;


    public AuthController(AuthenticationManager authenticationManager, CustomUserDetailService  customUserDetailService, JwtUtils jwtUtils) {
        this.authenticationManager = authenticationManager;
        this.customUserDetailService = customUserDetailService;
        this.jwtUtils = jwtUtils;
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(@RequestBody LoginRequest body) throws AuthenticationException {
        String userName = body.username();
        String password = body.password();
        var authentication = new UsernamePasswordAuthenticationToken(userName, password);
        Authentication auth = authenticationManager.authenticate(authentication);
        UserDetails userDetails = (UserDetails) auth.getPrincipal();
        String token = jwtUtils.generateToken(userDetails.getUsername());
        return ResponseEntity.ok(new LoginResponseDTO(token));
    }

    @PostMapping("/signUp")
    public ResponseEntity<SignUpResponseDTO> signUp (@RequestBody UserNamePasswordSingUp body){

        String username = body.username();
        String password = body.password();
        String bio = body.bio();

        SignUpResponseDTO res = customUserDetailService.registerUser(username, password, bio);

        return ResponseEntity.ok(res);
    }

}
