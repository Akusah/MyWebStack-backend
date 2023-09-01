package com.example.sys.entity.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: Leo
 * @Date: 2023/08/23/下午1:33
 * @Description:
 */
@Data
public class AuthorizeVo {
    String username;
    String role;
    String token;
    @JsonFormat(locale = "zh", timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    Date expire;
}
