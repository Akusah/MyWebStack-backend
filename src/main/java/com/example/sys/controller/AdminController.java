package com.example.sys.controller;


import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.example.common.vo.Result;
import com.example.sys.entity.*;
import com.example.sys.service.*;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: Leo
 * @Date: 2023/08/27/下午7:45
 * @Description:
 */
@Validated //开启验证接口是否合法
@RestController
@RequestMapping("/api/admin")
public class AdminController {
    @Autowired
    IUserService userService;

    @Autowired
    ICommentService commentService;

    @Autowired
    IWebinfoService webinfoService;

    @Autowired
    IWebService webService;

    @Autowired
    ICategoryService categoryService;

    @Autowired
    private RedissonClient redissonClient;


    /**
     * 获取所有的用户
     *
     * @return {@link Result}<{@link List}<{@link User}>>
     */
    @GetMapping("/getAllUser")
    public Result<?> getAllUser(@RequestParam(defaultValue = "1") Integer currentPage,
                                             @RequestParam(defaultValue = "8") Integer pageSize){

        Map<String,Object> userPage = userService.getUsersByPage(currentPage,pageSize);
        return Result.success(userPage);
    }

    /**
     * 删除账户
     *
     * @param userId 用户id
     * @return {@link Result}<{@link ?}>
     */
    @PostMapping("/delete/{userId}")
    public Result<?> deleteAccount(@PathVariable Integer userId){
        try {
            UpdateWrapper<User> userUpdateWrapper = new UpdateWrapper<>();
            userUpdateWrapper.eq("id",userId).set("disable",1);
            userService.update(null,userUpdateWrapper);
            return Result.success();
        } catch (Exception e) {
            e.printStackTrace();
            return Result.fail("发生未知错误，删除失败");
        }
    }

    /**
     * 更新信息
     *
     * @param user 用户
     * @return {@link Result}<{@link ?}>
     */

    @PostMapping("/edit")
    public Result<?> updateInfo(@RequestBody User user){
        try {
            userService.updateById(user);
            return Result.success("更新用户成功");
        } catch (Exception e) {
            e.printStackTrace();
            return Result.fail("发生未知错误，更新用户失败");
        }
    }


    /**
     * 获取所有评论
     *
     * @return {@link Result}<{@link List}<{@link Comment}>>
     */

    @GetMapping("/getAllComments")
    public Result<List<Comment>> getAllComments(){
        try {
            List<Comment> list = commentService.list();
            for(Comment ls : list){
                ls.setCurrentUsername(userService.getById(ls.getUserId()).getUsername());
                if (ls.getTarget()!=null){
                    ls.setTargetName(userService.getById(ls.getTarget()).getUsername());
                }
                ls.setCreatetime(ls.getCreatetime().replace("T"," "));
            }
            return Result.success(list,"获取评论成功");
        } catch (Exception e) {
            e.printStackTrace();
            return Result.fail("获取失败");
        }
    }

    /**
     * 分页评论
     *
     * @param currentPage 当前页面
     * @param pageSize    页面大小
     * @return {@link Result}<{@link List}<{@link Comment}>>
     */

    @GetMapping("/getCommentsByPage")
    public Result<?> getCommentsByPage( @RequestParam(defaultValue = "1") Integer currentPage,
                                        @RequestParam(defaultValue = "10") Integer pageSize){

        Map<String,Object> commentsPage = commentService.getAllCommentsByPage(currentPage,pageSize);

        return Result.success(commentsPage);
    }


    /**
     * 获取指定数量的Web点击量信息
     *
     * @param num
     * @return {@link Result}<{@link ?}>
     */

    @GetMapping("/getWebInfo")
    public Result<?> getWebInfo(@RequestParam(defaultValue = "5") Integer num) {
        List<Webinfo> webinfoList = webinfoService.list(); // 获取所有 Web 信息列表
        webinfoList.sort(Comparator.comparingInt(Webinfo::getClickCount).reversed()); // 根据点击量倒序排序
        for(Webinfo webinfo : webinfoList){
            webinfo.setWebTitle(webService.getWebTitle(webinfo.getWebId()));
        }
        return Result.success(webinfoList.subList(0, Math.min(num, webinfoList.size()))); // 返回点击量最多的五个 Web 信息
    }


    /**
     * 分页获取Web
     *
     * @param currentPage
     * @param pageSize
     * @return {@link Result}<{@link ?}>
     */

    @GetMapping("/getWebsByPage")
    public Result<?> getWebsByPage(@RequestParam(defaultValue = "1") Integer currentPage,
                                           @RequestParam(defaultValue = "5") Integer pageSize){

        Map<String,Object> webPage = webService.getWebsByPage(currentPage,pageSize);
        return Result.success(webPage);
    }

    /**
     * 更新网站
     *
     * @param web Web实体类
     * @return {@link Result}<{@link ?}>
     */

    @PostMapping("/editWeb")
    public Result<?> updateWeb(@RequestBody Web web){
        try {
            webService.updateById(web);
            return Result.success("更新成功");
        } catch (Exception e) {
            e.printStackTrace();
            return Result.fail("发生未知错误，更新网页失败");
        }
    }

    /**
     * 添加网站
     *
     * @param web Web实体类
     * @return {@link Result}<{@link ?}>
     */

    @PostMapping("/addWeb")
    public Result<?> addWeb(@RequestBody Web web){
        try {
            webService.save(web);
            return Result.success("保存成功");
        } catch (Exception e) {
            e.printStackTrace();
            return Result.fail("发生未知错误");
        }
    }


    /**
     * 获取主目录
     *
     * @return {@link Result}<{@link List}<{@link Category}>>
     */

    @GetMapping("/getMainCategory")
    public Result<List<Category>> getMainCategory(){
        List<Category> webList = categoryService.getMainCategory();
        return Result.success(webList,"获取成功！");
    }

    /**
     * 添加目录
     *
     * @param category
     * @return {@link Result}<{@link ?}>
     */

    @PostMapping("/addCategory")
    public Result<?> addCategory(@RequestBody Category category){
        try {
            if (categoryService.save(category)){
                // 删除Redis缓存
                redissonClient.getKeys().delete("categoryList");
                return Result.success("添加目录成功");
            }else {
                return Result.fail("发生未知错误，添加目录失败");
            }
        }catch (Exception e){
            e.printStackTrace();
            return Result.fail("发生未知错误，添加目录失败");
        }
    }

    /**
     * 删除目录
     *
     * @param list
     * @return {@link Result}<{@link ?}>
     */

    @PostMapping("/deleteCategory")
    public Result<?> deleteCategory(@RequestBody ArrayList<Integer> list){
        redissonClient.getKeys().delete("categoryList");
        return categoryService.deleteCategory(list);
    }

    /**
     * 上传图片logo
     *
     * @param file
     * @return {@link Result}<{@link ?}>
     */

    @PostMapping("/uploadFile")
    public Result<?> uploadFile(@RequestParam(value = "file",required = false) MultipartFile file){
        if (file.isEmpty()){
            return Result.fail();
        }
        String OriginalFilename = file.getOriginalFilename();

        //为了防止重名覆盖，获取系统时间戳+原始文件的后缀名
        String fileName = System.currentTimeMillis()+"." + OriginalFilename.substring(OriginalFilename.lastIndexOf(".")+1);
        //设置后台保存位置
        String path = "C:\\Users\\10184\\Desktop\\Projects\\WebProject\\WebStack\\MyWebStack-frontend\\public\\assets\\images\\logos\\";
        File dest = new File(path+fileName);

        if (!dest.getParentFile().exists()){
            dest.getParentFile().mkdirs();
        }
        try {
            file.transferTo(dest);
            return Result.success("图片上传成功！",fileName);
        } catch (IOException e) {
            e.printStackTrace();
            return Result.fail();
        }
    }


}
