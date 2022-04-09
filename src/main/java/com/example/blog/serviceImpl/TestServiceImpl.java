package com.example.blog.serviceImpl;

import com.example.blog.config.database.dao.UserDao;
import com.example.blog.config.database.dto.DBResponse;
import com.example.blog.config.database.dto.UserParam;
import com.example.blog.config.database.dto.UserResponse;
import com.example.blog.service.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class TestServiceImpl implements TestService {

    @Autowired
    UserDao userDao;

    @Override
    public UserResponse getUserById(Integer id) {
        String SESSION_ID = UUID.randomUUID().toString();
        UserParam param = new UserParam();
        param.setUserId(id);
        DBResponse<List<UserResponse>> response = userDao.getUserV1(SESSION_ID, param);
        UserResponse result = response.getResult().get(0);
        return result;
    }
}
