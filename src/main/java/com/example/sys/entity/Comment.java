package com.example.sys.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

/**
 * <p>
 * 
 * </p>
 *
 * @author xyh
 * @since 2023-08-09
 */
@TableName("db_comment")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Comment implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 评论id
     */
    @TableId(value = "comment_id", type = IdType.AUTO)
    private Integer commentId;

    /**
     * 用户id
     */
    private Integer userId;

    /**
     * 网站评分
     */
    private BigDecimal rate;

    /**
     * 内容
     */
    private String content;

    /**
     * 业务模块的id，用于区分不同网站
     */
    private Integer webId;

    /**
     * 回复对象
     */
    private Integer target;

    /**
     * 	父级评论id
     */
    private Integer pid;

    /**
     * 创建时间
     */
    private String createtime;

    /**
     * 子评论
     */
    @TableField(exist = false)
    private List<Comment> children;

    /**
     * 当前评论用户的用户名
     */
    @TableField(exist = false)
    private String currentUsername;

    /**
     * 当前评论对象的用户名
     */
    @TableField(exist = false)
    private String targetName;

    @TableField(exist = false)
    private String webTitle;
}
