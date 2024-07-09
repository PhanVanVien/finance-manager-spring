package com.minimalcode.financemanager.service;

import com.minimalcode.financemanager.dto.auth.request.RegisterRequest;

public interface UserService {
    void register(RegisterRequest request);
}
