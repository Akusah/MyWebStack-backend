package com.example.sys.controller;

import com.example.common.vo.Result;
import com.example.sys.entity.Webinfo;
import com.example.sys.service.IWebinfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Comparator;
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
@RequestMapping("/sys/webinfo")
public class WebinfoController {

    @Autowired
    private IWebinfoService webinfoService;


    /**
     * 增加点击量
     *
     * @param webId
     * @return {@link Result}<{@link ?}>
     */

    @PostMapping("/addCount/{webId}")
    public Result<?> addCount(@PathVariable Integer webId){
        try {
            Webinfo webinfo = null;
            webinfo = webinfoService.getById(webId);
            if (webinfo != null){
                int count = webinfo.getClickCount();
                webinfo.setClickCount(count+1);
                webinfoService.updateById(webinfo);
            }else {
                Webinfo newinfo = new Webinfo(webId,1);
                webinfoService.save(newinfo);
            }
            return Result.success();
        } catch (Exception e) {
            e.printStackTrace();
            return Result.fail();
        }
    }

}
