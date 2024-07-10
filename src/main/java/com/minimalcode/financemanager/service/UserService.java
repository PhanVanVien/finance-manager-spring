package com.minimalcode.financemanager.service;

import com.minimalcode.financemanager.dto.auth.request.RegisterRequest;
import com.minimalcode.financemanager.model.user.User;

public interface UserService {
    void register(RegisterRequest user);
}
