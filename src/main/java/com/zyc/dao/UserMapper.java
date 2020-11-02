package com.zyc.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zyc.entity.User;
import org.springframework.stereotype.Repository;

@Repository
public interface UserMapper extends BaseMapper<User> {
}
