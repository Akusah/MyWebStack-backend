package com.example.sys.service;

import com.example.sys.entity.Web;
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
public interface IWebService extends IService<Web> {

    List<Web> getWebs(Integer categoryId);

    Map<String,Object> getWebsByPage(Integer currentPage, Integer pageSize);

    String getWebTitle(Integer webId);

}
