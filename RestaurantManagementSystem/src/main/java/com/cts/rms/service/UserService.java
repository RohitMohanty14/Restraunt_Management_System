package com.cts.rms.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.cts.rms.model.User;
import com.cts.rms.repository.UserRepository;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;
    
    public List<User> listAllUsers() {
        return userRepository.findAll();
    }

    public User getById(Long id) {
        return userRepository.findById(id).orElse(null);
    }
    public User addUser(User user) {
    	user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    
    public Boolean removeUser(Long id) {
        if (userRepository.existsById(id)) {
            userRepository.deleteById(id);
            return true;
        }
        return false;
    }

   
    public User updateUser(User updatedUser, Long id) {
        return userRepository.findById(id).map(u -> {
            u.setUserName(updatedUser.getUserName());
            u.setPassword(passwordEncoder.encode(updatedUser.getPassword()));
            u.setEmail(updatedUser.getEmail());
            u.setRole(updatedUser.getRole());
            return userRepository.save(u);
        }).orElse(null);
    }

    
    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

}
