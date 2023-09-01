package com.example.sys.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author xyh
 * @since 2023-08-09
 */
@TableName("db_web")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Web implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 网站id
     */
    @TableId(value = "web_id", type = IdType.AUTO)
    private Integer webId;

    /**
     * 网站名
     */
    private String webTitle;

    /**
     * 网页描述
     */
    private String webDesc;

    /**
     * url路径
     */
    private String url;

    /**
     * logo路径
     */
    private String logo;

    /**
     * 目录id
     */
    private Integer categoryId;


    /**
     * 网站状态，1可见，0不可见
     */
    private Integer status;

    @TableField(exist = false)
    private String categoryName;

}
