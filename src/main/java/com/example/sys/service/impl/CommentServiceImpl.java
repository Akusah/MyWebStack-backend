package com.example.sys.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.common.vo.Result;
import com.example.sys.entity.Comment;
import com.example.sys.mapper.CommentMapper;
import com.example.sys.mapper.UserMapper;
import com.example.sys.mapper.WebMapper;
import com.example.sys.service.ICommentService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.sys.service.IUserService;
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
public class CommentServiceImpl extends ServiceImpl<CommentMapper, Comment> implements ICommentService {

    @Resource
    private CommentMapper commentMapper;

    @Resource
    private IUserService userService;

    @Resource
    private WebMapper webMapper;

    @Override
    public List<Comment> getCommentsInWeb(Integer webId) {
        return this.baseMapper.getComments(webId);
    }

    @Override
    public Map<String,Object> getAllCommentsByPage(Integer currentPage, Integer pageSize) {
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
        Page<Comment> commentPage = this.baseMapper.selectPage(new Page<>(currentPage,pageSize),null);
        List<Comment> commentList = commentPage.getRecords();
        for (Comment comment : commentList){
            comment.setCurrentUsername(userService.getById(comment.getUserId()).getUsername());
            comment.setWebTitle(webMapper.getWebTitle(comment.getWebId()));
            if (comment.getTarget()!=null){
                comment.setTargetName(userService.getById(comment.getTarget()).getUsername());
            }
        }
        data.put("pageInfo",pageInfo);
        data.put("pageComments",commentList);
        return data;
    }

    @Override
    public Map<String,Object> getPaticularCommentsByPage(Integer webId,Integer currentPage, Integer pageSize) {

        Map<String,Object> data = new HashMap<>();

        //获取总记录数
        int totalRecords = this.baseMapper.selectList(new LambdaQueryWrapper<Comment>().eq(Comment::getWebId,webId).isNull(Comment::getPid)).size();
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

        Page<Comment> commentPage = this.baseMapper.selectPage(new Page<>(currentPage,pageSize),new LambdaQueryWrapper<Comment>().eq(Comment::getWebId,webId).isNull(Comment::getPid));

        data.put("pageInfo",pageInfo);
        data.put("pageComments",commentPage.getRecords());
        return data;
    }

    @Override
    public Integer postComment(Comment comment) {
        try {
            System.out.println(comment);
            int i = commentMapper.insertComment(comment);
            return i;
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }


}
