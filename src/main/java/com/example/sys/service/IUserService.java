package com.example.sys.service;

import com.example.sys.entity.User;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author xyh
 * @since 2023-08-09
 */
public interface IUserService extends IService<User> {


    Map<String,Object>  getUsersByPage(Integer currentPage, Integer pageSize);

    User getUserByToken(String token);
}
