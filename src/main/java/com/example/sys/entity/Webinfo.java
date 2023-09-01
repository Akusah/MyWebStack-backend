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
@TableName("db_webinfo")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Webinfo implements Serializable {

    private static final long serialVersionUID = 1L;
    @TableId(value = "web_id", type = IdType.AUTO)
    private Integer webId;

    private Integer clickCount;

    @TableField(exist = false)
    private String webTitle;


    public Webinfo(Integer webId,Integer clickCount){
        this.webId = webId;
        this.clickCount = clickCount;
    }


}
