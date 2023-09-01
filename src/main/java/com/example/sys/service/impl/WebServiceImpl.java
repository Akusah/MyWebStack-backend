package com.example.sys.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.sys.entity.Comment;
import com.example.sys.entity.Web;
import com.example.sys.mapper.CategoryMapper;
import com.example.sys.mapper.WebMapper;
import com.example.sys.service.IWebService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
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
public class WebServiceImpl extends ServiceImpl<WebMapper, Web> implements IWebService {

    @Resource
    private WebMapper webMapper;

    @Resource
    private CategoryMapper categoryMapper;

    @Override
    public List<Web> getWebs(Integer categoryId) {
        return this.baseMapper.getWebs(categoryId);
    }

    @Override
    public Map<String,Object> getWebsByPage(Integer currentPage, Integer pageSize) {
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
        Page<Web> webPage = this.baseMapper.selectPage(new Page<>(currentPage,pageSize),null);
        List<Web> webList = webPage.getRecords();
        for (Web web : webList){
            web.setCategoryName(categoryMapper.selectById(web.getCategoryId()).getCategoryName());
        }
        data.put("pageInfo",pageInfo);
        data.put("pageWeb",webList);
        return data;
    }

    @Override
    public String getWebTitle(Integer webId) {
        return webMapper.selectOne(new LambdaQueryWrapper<Web>().eq(Web::getWebId,webId)).getWebTitle();
    }

}
