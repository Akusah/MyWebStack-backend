package com.example.sys.mapper;

import com.example.common.vo.Result;
import com.example.sys.entity.Comment;
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
public interface CommentMapper extends BaseMapper<Comment> {

    List<Comment> getComments(Integer webId);

    Integer insertComment(Comment comment);
}
