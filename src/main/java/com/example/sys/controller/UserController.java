package com.example.sys.controller;

import com.example.common.vo.Result;
import com.example.sys.entity.Comment;
import com.example.sys.entity.User;
import com.example.sys.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;


import java.util.List;
import java.util.Map;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author xyh
 * @since 2023-08-09
 */
@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    private IUserService userService;

    @Autowired
    private PasswordEncoder passwordEncoder;


    /**
     * 根据token获取用户信息
     *
     * @param token
     * @return {@link Result}<{@link User}>
     */

    @GetMapping("/getUserByToken")
    public Result<User> getUserByToken(@RequestParam("token") String token){
        User loginUser = userService.getUserByToken(token);
        if(loginUser != null){
            return Result.success(loginUser);
        }
        return Result.fail(203,"登录信息无效，请重新登录");
    }






}
