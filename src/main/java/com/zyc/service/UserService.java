package com.zyc.service;

import com.zyc.entity.User;

public interface UserService {

    User selectById(Integer userId);

    void deleteById(Integer userId);

    void insert(User user);

}
