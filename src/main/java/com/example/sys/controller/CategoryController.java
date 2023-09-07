package com.example.sys.controller;

import com.example.common.vo.Result;
import com.example.sys.entity.Category;
import com.example.sys.service.ICategoryService;
import com.example.sys.service.IWebService;
import org.redisson.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author xyh
 * @since 2023-08-09
 */
@RestController
@RequestMapping("/sys/category")
public class CategoryController {

    @Autowired
    private ICategoryService categoryService;

    @Autowired
    private IWebService webService;

    @Autowired
    private RedissonClient redissonClient;


    /**
     * 获取目录列表
     *
     * @return {@link Result}<{@link List}<{@link Category}>>
     */

    @GetMapping("/getCategoryList")
    public Result<List<Category>> getCategoryList(){
        try {
            // 获取RList对象
            RList<Category> redisList = redissonClient.getList("categoryList");
            List<Category> categories = new ArrayList<>();
            if (redisList.isEmpty()){
                categories = categoryService.getWebList();
                // 将数据添加到RList中
                redisList.addAll(categories);
                return Result.success(categories,"获取网页列表成功！");
            }else{
                return Result.success(redisList);
            }

        } catch (Exception e) {
            e.printStackTrace();
            return Result.fail("获取网页列表失败");
        }

    }

    /**
     * 获取当前具有网站列表的一级或二级目录
     *
     * @return {@link Result}<{@link List}<{@link Category}>>
     */

    @GetMapping("/getWebListUnderCategory")
    public Result<List<Category>> getWebListUnderCategory(){
        //获取当前具有网站列表的一级或二级目录
        List<Category> categories = categoryService.getWebList();
        for (Category category : categories){
            category.setWebList(webService.getWebs(category.getCategoryId()));
        }
        return Result.success(categories,"获取网站成功");

    }

}
