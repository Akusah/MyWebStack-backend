package com.example.sys.service;

import com.example.sys.entity.Category;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.sys.entity.Web;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author xyh
 * @since 2023-08-09
 */
public interface ICategoryService extends IService<Category> {

    List<Category> getWebList();

    List<Category> getMainCategory();
}
