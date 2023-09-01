package com.example.sys.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.example.common.vo.Result;
import com.example.sys.entity.Comment;
import com.example.sys.service.ICommentService;
import com.example.sys.service.IUserService;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author xyh
 * @since 2023-08-09
 */
@RestController
@RequestMapping("/sys/comment")
public class CommentController {
    @Autowired
    private ICommentService commentService;

    @Autowired
    private IUserService userService;

    @Autowired
    private RedissonClient redissonClient;





    @PostMapping("/posted")
    public Result<?> PostComment(@RequestBody Comment comment) {
        try {
            if (commentService.postComment(comment) != 0) {
                return Result.success("评论发布成功");
            }else {
                return Result.fail("评论发布失败，请稍后重试");
            }
        } catch (Exception e){
            e.printStackTrace();
        }
        return Result.fail("评论发布失败，请稍后重试2");
    }

    @GetMapping("/getCommentByWebId/{webId}")
    public Result<?> getCommentByWebId(@PathVariable Integer webId,
                                       @RequestParam(defaultValue = "1") Integer currentPage,
                                       @RequestParam(defaultValue = "5") Integer pageSize){
        try {
            // 获取指定webId对应的所有评论
            List<Comment> comments = commentService.getCommentsInWeb(webId);

            Map<String,Object> map = new HashMap<>();
            BigDecimal rate = BigDecimal.ZERO;
            map.put("rate",BigDecimal.ZERO);

            // 筛选出具有评分的评论并计算平均评分
            List<Comment> commentList = comments.stream().filter(comment -> comment.getRate() != null).collect(Collectors.toList());
            commentList.stream().map(Comment::getRate).reduce(BigDecimal::add).ifPresent(res->{
                map.put("rate",res.divide(BigDecimal.valueOf(commentList.size()),1, RoundingMode.HALF_UP));
            });

            // 获取所有顶级评论（没有父评论）及其子评论
//            List<Comment> rootComments = comments.stream().filter(comment -> comment.getPid() == null).collect(Collectors.toList());
            // List<Comment> rootComments = commentService.getPaticularCommentsByPage(webId,currentPage,pageSize);
            Map<String,Object> rootCommentsPage = commentService.getPaticularCommentsByPage(webId,currentPage,pageSize);
            List<Comment> rootComments = (List<Comment>) rootCommentsPage.get("pageComments");
            for (Comment rootComment : rootComments) {
                // 设置当前顶级评论的用户名
                rootComment.setCurrentUsername(userService.getById(rootComment.getUserId()).getUsername());
                List<Comment> children = comments.stream().filter(comment -> Objects.equals(rootComment.getCommentId(), comment.getPid())).collect(Collectors.toList());
                for (Comment childComment : children) {
                    // 设置子评论的用户名和目标名字
                    childComment.setCurrentUsername(userService.getById(childComment.getUserId()).getUsername());
                    childComment.setTargetName(userService.getById(childComment.getTarget()).getUsername());
                }
                // 设置子评论的用户名和目标名字
                rootComment.setChildren(children);
            }
            rootCommentsPage.remove("pageComments");
            rootCommentsPage.put("comments",rootComments);

            // 将顶级评论列表和平均评分放入map中
            map.put("pageComment",rootCommentsPage);
            return Result.success(map);
        } catch (Exception e) {
            e.printStackTrace();
            return Result.fail("获取评论失败，请稍后重试");
        }
    }

    @PostMapping("/delete/{commentId}")
    public Result<?> deleteComment(@PathVariable Integer commentId){
        try {
            return Result.success("删除评论成功！");
        } catch (Exception e) {
            e.printStackTrace();
            return Result.fail("删除评论失败！");
        }
    }

}
