package com.example.sys.mapper;

import com.example.sys.entity.Category;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.sys.entity.Web;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author xyh
 * @since 2023-08-09
 */
public interface CategoryMapper extends BaseMapper<Category> {

    List<Category> getMainCategory();

    List<Category> getChildrenCategory(Integer categoryId);


}
