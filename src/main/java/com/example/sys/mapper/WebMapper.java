package com.example.sys.mapper;

import com.example.sys.entity.Web;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author xyh
 * @since 2023-08-09
 */
public interface WebMapper extends BaseMapper<Web> {

    List<Web> getWebs(Integer categoryId);

    String getWebTitle(Integer webId);
}
