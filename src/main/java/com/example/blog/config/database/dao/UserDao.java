package com.example.blog.config.database.dao;

import com.example.blog.config.database.dto.DBResponse;
import com.example.blog.config.database.dto.UserParam;
import com.example.blog.config.database.dto.UserResponse;
import com.example.blog.config.database.service.BaseService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserDao extends BaseService {
    private static final String GET_USER = "get_user";

    /* BEGIN GET */
    public DBResponse<List<UserResponse>> getUserV1(String sessionId, UserParam params) {
        return this.dbGet(sessionId, GET_USER, params, UserResponse.class);
    }
    /* END GET */

}
