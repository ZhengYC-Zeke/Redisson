package com.zyc.service.impl;

import com.zyc.dao.UserMapper;
import com.zyc.entity.User;
import com.zyc.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    private final UserMapper userMapper;

    public UserServiceImpl(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    @Override
    public User selectById(Integer userId) {
        return userMapper.selectById(userId);
    }

    @Override
    public void deleteById(Integer userId) {
        userMapper.deleteById(userId);
    }

    @Override
    public void insert(User user) {
        userMapper.insert(user);
    }
}
