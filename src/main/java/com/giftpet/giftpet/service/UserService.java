package com.giftpet.giftpet.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.giftpet.giftpet.model.User;
import com.giftpet.giftpet.repository.UserRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class UserService {
    private UserRepository repository;
    private PasswordEncoder encoder;

    public User createUser(String name, String email, String password, boolean admin) {
        User user = new User();
        user.setName(name);
        user.setEmail(email);
        user.setPassword(encoder.encode(password));
        user.setAdmin(admin);

        return repository.save(user);
    }
}
