package com.example.sys.service.impl;

import com.auth0.jwt.interfaces.DecodedJWT;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.common.utils.JwtUtils;
import com.example.common.vo.Result;
import com.example.sys.entity.User;
import com.example.sys.mapper.UserMapper;
import com.example.sys.service.IUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author xyh
 * @since 2023-08-09
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtUtils jwtUtils;

    @Resource
    private UserMapper userMapper;



    @Override
    public Map<String,Object>  getUsersByPage(Integer currentPage, Integer pageSize) {
        Map<String,Object> data = new HashMap<>();
        //获取总记录数
        int totalRecords = this.baseMapper.selectList(null).size();
        // 计算总页数
        int totalPages = (int)Math.ceil((double)totalRecords / pageSize);
        // 判断 currentPage 是否合法
        if (currentPage <= 0) {
            currentPage = 1; // 将 currentPage 调整为最小合法值
        } else if (currentPage > totalPages) {
            currentPage = totalPages; // 将 currentPage 调整为最大合法值
        }
        Map<String,Object> pageInfo = new HashMap<>();
        pageInfo.put("total",totalRecords);
        pageInfo.put("pageCount",totalPages);
        pageInfo.put("currentPage",currentPage);

        Page<User> userPage = this.baseMapper.selectPage(new Page<>(currentPage,pageSize),null);
        data.put("pageInfo",pageInfo);
        data.put("pageUser",userPage.getRecords());
        return data;
    }

    @Override
    public User getUserByToken(String token) {
        DecodedJWT loginToken = jwtUtils.resolveJwt(token);
        Integer userId = jwtUtils.toId(loginToken);
        User loginUser = userMapper.selectOne(new LambdaQueryWrapper<User>().eq(User::getId,userId));

        loginUser.setPassword("??????");

        return loginUser;
    }


}
