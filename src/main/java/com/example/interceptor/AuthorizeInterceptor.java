package com.example.interceptor;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.sys.entity.User;
import com.example.sys.mapper.UserMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: Leo
 * @Date: 2023/07/06/下午9:36
 * @Description:
 */
@Component
@Slf4j
public class AuthorizeInterceptor implements HandlerInterceptor {

    @Resource
    private UserMapper userMapper;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        SecurityContext context = SecurityContextHolder.getContext();
        Authentication authentication = context.getAuthentication();
        String name = authentication.getName();
        User user = userMapper.selectOne(new QueryWrapper<User>().eq("username",name).or().eq("email",name));

        return true;
    }
}
