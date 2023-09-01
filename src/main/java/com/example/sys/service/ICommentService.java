package com.example.sys.service;

import com.example.sys.entity.Comment;
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
public interface ICommentService extends IService<Comment> {

    List<Comment> getCommentsInWeb(Integer webId);

    Map<String,Object> getAllCommentsByPage(Integer currentPage, Integer pageSize);

    Map<String,Object> getPaticularCommentsByPage(Integer webId, Integer currentPage, Integer pageSize);

    Integer postComment(Comment comment);
}
