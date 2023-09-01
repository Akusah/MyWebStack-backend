package com.example.sys.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * <p>
 * 
 * </p>
 *
 * @author xyh
 * @since 2023-08-09
 */
@TableName("db_category")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Category implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 目录分类id
     */
    @TableId(value = "category_id", type = IdType.AUTO)
    private Integer categoryId;

    /**
     * 目录分类名
     */
    private String categoryName;

    /**
     * 目录英文名
     */
    private String categoryEngname;

    /**
     * 目录日文名
     */
    private String categoryJpname;

    /**
     * icon样式名
     */
    private String icon;

    /**
     * 父目录
     */
    private Integer pid;

    /**
     * 目录下的子目录
     */
    @TableField(exist = false)
    private List<Category> childrenCategory;

    /**
     * 目录下的网站列表
     */
    @TableField(exist = false)
    private List<Web> webList;





}
