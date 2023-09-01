package com.example.sys.service;

import org.springframework.security.core.userdetails.UserDetailsService;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: Leo
 * @Date: 2023/08/18/下午7:21
 * @Description:
 */
public interface IAuthorizeServiceUser extends UserDetailsService {
    String register(String username, String password, String email, String code, String id);
}
