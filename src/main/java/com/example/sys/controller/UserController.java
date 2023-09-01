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




//    /**
//     * 注册方法
//     *
//     * @param user 用户
//     * @return {@link Result}<{@link ?}>
//     */
//    @PostMapping("/register")
//    public Result<?> register(@RequestBody User user){
//        try {
//            user.setPassword(passwordEncoder.encode(user.getPassword()));
//            userService.save(user);
//            return Result.success("注册成功！");
//        } catch (Exception e) {
//            e.printStackTrace();
//            return Result.fail("发生未知错误，注册失败");
//        }
//    }


    @GetMapping("/getUserByToken")
    public Result<User> getUserByToken(@RequestParam("token") String token){
        User loginUser = userService.getUserByToken(token);
        if(loginUser != null){
            return Result.success(loginUser);
        }
        return Result.fail(203,"登录信息无效，请重新登录");
    }






}
