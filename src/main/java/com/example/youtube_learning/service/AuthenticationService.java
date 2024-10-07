package com.example.youtube_learning.service;

import com.example.youtube_learning.dto.LoginRequest;
import com.example.youtube_learning.dto.LoginResponse;
import com.example.youtube_learning.entity.Users;

public interface AuthenticationService {
    LoginResponse authenticate(LoginRequest loginRequest);  // For sign-in logic

    Users signup(Users user);  // For sign-up logic
}
