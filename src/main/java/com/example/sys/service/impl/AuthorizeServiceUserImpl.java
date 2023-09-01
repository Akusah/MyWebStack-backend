package com.example.sys.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.sys.entity.User;
import com.example.sys.mapper.UserMapper;
import com.example.sys.service.IAuthorizeServiceUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Objects;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: Leo
 * @Date: 2023/08/18/下午7:22
 * @Description:
 */
@Service
public class AuthorizeServiceUserImpl implements IAuthorizeServiceUser {

    @Resource
    private UserMapper userMapper;

    @Override
    public String register(String username, String password, String email, String code, String id) {
        return "注册成功";
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userMapper.selectOne(new QueryWrapper<User>().eq("username", username).or().eq("email",username));
        if (Objects.isNull(user)){
            throw new UsernameNotFoundException("用户名或密码错误");
        }
        return org.springframework.security.core.userdetails.User
                .withUsername(username)
                .password(user.getPassword())
                .roles(user.getIsAdmin() == 1 ? "admin" : "user")
                .build();
    }
}
