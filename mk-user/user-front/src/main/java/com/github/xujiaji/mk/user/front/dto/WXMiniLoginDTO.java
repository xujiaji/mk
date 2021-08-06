package com.github.xujiaji.mk.user.front.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * @author jiajixu
 * @date 2020/8/24 11:41
 */
@Data
public class WXMiniLoginDTO {
    @JsonProperty("session_key")
    private String sessionKey;
    @JsonProperty("openid")
    private String openid;
    @JsonProperty("unionid")
    private String unionid;
    @JsonProperty("errcode")
    private Integer errcode;
    @JsonProperty("errmsg")
    private String errmsg;
}
