package com.minimalcode.financemanager.service.impl;

import com.minimalcode.financemanager.dto.auth.request.RegisterRequest;
import com.minimalcode.financemanager.model.user.Role;
import com.minimalcode.financemanager.model.user.User;
import com.minimalcode.financemanager.repository.UserRepository;
import com.minimalcode.financemanager.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void register(RegisterRequest user) {
        User newUser = new User();
        newUser.setFirstName(user.getFirstName());
        newUser.setLastName(user.getLastName());
        newUser.setEmail(user.getEmail());
        newUser.setPassword(user.getPassword());
        newUser.setRole(Role.ROLE_USER);
        userRepository.save(newUser);
    }

    @Override
    public List<User> getList() {
        return userRepository.findAll();
    }
}
