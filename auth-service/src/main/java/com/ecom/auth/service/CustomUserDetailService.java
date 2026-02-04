package com.ecom.auth.service;

import com.ecom.auth.config.CustomUserDetail;
import com.ecom.auth.dtos.responseDTOs.SignUpResponseDTO;
import com.ecom.auth.repository.UserRepository;
import com.ecom.auth.entities.User;
import com.ecom.auth.util.JwtUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CustomUserDetailService implements UserDetailsService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtils jwtUtils;

    public CustomUserDetailService(UserRepository userRepository, PasswordEncoder passwordEncoder,  JwtUtils jwtUtils) {
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
        this.jwtUtils = jwtUtils;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username)
                .map(CustomUserDetail::new)
                .orElseThrow(() ->
                        new UsernameNotFoundException(
                                "User not found with username: " + username
                        )
                );
    }

    public SignUpResponseDTO registerUser(String username, String password, String bio) {
        Optional<User> oldUser = userRepository.findByUsername(username);
        if(oldUser.isPresent()) {
            throw new UsernameNotFoundException("Username already exists");
        }
        String passwordHash = passwordEncoder.encode(password);
        User newUser  = new User(username, passwordHash,bio);
        User savedUser = userRepository.save(newUser);
        String token = jwtUtils.generateToken(savedUser.getUsername());
        return new SignUpResponseDTO(token);
    }
}
