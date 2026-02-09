package com.ecom.authservice.user.service;

import com.ecom.authservice.user.repository.UserRepository;
import com.ecom.authservice.user.entities.User;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
    }


    public User registerUser(String username, String password, String bio) {
        Optional<User> oldUser = userRepository.findByUsername(username);
        if(oldUser.isPresent()) {
            throw new IllegalArgumentException("Username already exists");
        }
        String passwordHash = passwordEncoder.encode(password);
        User newUser  = new User(username, passwordHash,bio);
        return userRepository.save(newUser);
    }
}
