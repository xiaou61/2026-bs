package com.xiaou.wedding.service;

import com.xiaou.wedding.dto.LoginRequest;
import com.xiaou.wedding.dto.RegisterRequest;
import com.xiaou.wedding.vo.LoginResponse;

public interface UserService {
    LoginResponse login(LoginRequest request);
    
    void register(RegisterRequest request);
}
