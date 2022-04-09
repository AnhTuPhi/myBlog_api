package com.example.blog.service;

import com.example.blog.config.database.dto.UserResponse;

public interface TestService {
    UserResponse getUserById(Integer id);
}
