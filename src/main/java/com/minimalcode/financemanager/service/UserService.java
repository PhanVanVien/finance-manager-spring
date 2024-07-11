package com.minimalcode.financemanager.service;

import com.minimalcode.financemanager.dto.auth.request.RegisterRequest;
import com.minimalcode.financemanager.model.user.User;

import java.util.List;

public interface UserService {
    void register(RegisterRequest user);

    List<User> getList();
}
