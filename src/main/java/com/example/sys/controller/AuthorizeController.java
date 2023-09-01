package com.example.sys.controller;

import com.example.common.vo.Result;
import com.example.sys.service.IAuthorizeServiceUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: Leo
 * @Date: 2023/08/18/下午7:18
 * @Description:
 */
@Validated //开启验证接口是否合法
@RestController
@RequestMapping("/api/auth")
public class AuthorizeController {

    @Autowired
    private IAuthorizeServiceUser authorizeServiceUser;

    //邮箱正则表达式
    private final String EMAIL_REGEXP = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";

    //发送邮箱功能


    @PostMapping("/register")
    public Result<?> register(@RequestParam("email") String email,
                              @RequestParam("username") String username,
                              @RequestParam("password") String password,
                              @RequestParam("code") String code,
                              HttpSession session
    ){
        String register = authorizeServiceUser.register(username,password,email,code,session.getId());
        if(register==null){
            return Result.success("注册成功！");
        }else
            return Result.fail(register);
    }

}
