package com.deepakgosavi.ecommerce.service;

import com.deepakgosavi.ecommerce.entity.User;
import com.deepakgosavi.ecommerce.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepo userRepo;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepo userRepo, PasswordEncoder passwordEncoder) {
        this.userRepo = userRepo;
        this.passwordEncoder = passwordEncoder;
    }


    public List<User> getAllUser() {
        return userRepo.findAll();
    }

    public User findByEmail(String email) {
        return userRepo.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User with email " + email + " not found."));
    }



    public User addUser(User user) {
        if (user.getPassword() == null || user.getPassword().isEmpty()) {
            throw new IllegalArgumentException("Password cannot be null or empty");
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepo.save(user);
    }


    public void deleteUser(Long id) {
        if (userRepo.existsById(id)) {
            userRepo.deleteById(id);
        } else {
            throw new RuntimeException("User with ID " + id + " not found.");
        }
    }


    public User updateUser(Long id, User user) {
        if (userRepo.existsById(id)) {
            user.setId(id);
            return userRepo.save(user);
        }
        throw new RuntimeException("User with ID " + id + " not found.");
    }
}
