package com.minimalcode.financemanager.service.impl;

import com.minimalcode.financemanager.dto.auth.request.RegisterRequest;
import com.minimalcode.financemanager.model.user.Role;
import com.minimalcode.financemanager.model.user.User;
import com.minimalcode.financemanager.repository.UserRepository;
import com.minimalcode.financemanager.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void register(RegisterRequest request) {
        User user = new User();
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRole(Role.ROLE_USER);
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        userRepository.save(user);
    }
}
