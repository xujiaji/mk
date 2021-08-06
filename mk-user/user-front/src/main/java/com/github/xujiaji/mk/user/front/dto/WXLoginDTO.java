package com.github.xujiaji.mk.user.front.dto;


import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author jiajixu
 * @date 2020-05-27 10:05
 */
@NoArgsConstructor
@Data
public class WXLoginDTO {
    private Integer errcode;
    private String errmsg;
    private String unionid;
    private String openid;
    private String nickname;
    private int sex;
    private String language;
    private String city;
    private String province;
    private String country;
    private String headimgurl;
    private List<?> privilege;
}
