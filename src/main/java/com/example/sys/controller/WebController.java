package com.example.sys.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.common.vo.Result;
import com.example.sys.entity.Comment;
import com.example.sys.entity.User;
import com.example.sys.entity.Web;
import com.example.sys.service.IWebService;
import org.redisson.api.RList;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
@RequestMapping("/sys/web")
public class WebController {

    @Autowired
    private IWebService webService;

    @Autowired
    private RedissonClient redissonClient;


    @GetMapping("/getWebList")
    public Result<List<Web>> getWebList(){
        List<Web> webList = webService.list();
        return Result.success(webList,"获取成功！");
    }




    /**
     * 根据目录id获取对应目录的网站列表
     *
     * @param categoryId 目录id
     * @return {@link Result}<{@link List}<{@link Web}>>
     */

    @GetMapping("/getWebs/{categoryId}")
    public Result<List<Web>> getWebs(@PathVariable Integer categoryId){
        try {
            List<Web> webs = webService.getWebs(categoryId);
            // 获取RList对象
            RList<Web> redisList = redissonClient.getList("webList");
            // 清空旧数据
            redisList.delete();
            // 将数据添加到RList中
            redisList.addAll(webs);
            return Result.success(webs,"获取网站列表成功！");
        } catch (Exception e) {
            e.printStackTrace();
            return Result.fail("获取失败");
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
            return Result.success("添加成功！");
        } catch (Exception e) {
            e.printStackTrace();
            return Result.fail("添加失败！");
        }
    }

    /**
     * 根据webId删除网站
     *
     * @param webId 网站id
     * @return {@link Result}<{@link ?}>
     */

    @PostMapping("/deleteWeb/{webId}")
    public Result<?> deleteWeb(@PathVariable Integer webId){
        try {
            webService.removeById(webId);
            return Result.success("删除网页成功！");
        } catch (Exception e) {
            e.printStackTrace();
            return Result.fail("删除失败！");
        }
    }




    @GetMapping("/getWebById/{webId}")
    public Result<Web> getWebById(@PathVariable Integer webId){
        try {
            Web web = webService.getOne(new QueryWrapper<Web>().eq("web_id", webId));
            return Result.success(web,"获取网站信息成功");
        } catch (Exception e) {
            e.printStackTrace();
            return Result.fail();
        }
    }



}
