package com.github.xujiaji.mk.user.front.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * @author jiajixu
 * @date 2020-05-26 16:58
 */
@Data
public class QQLoginDTO {
    private Integer error;
    @JsonProperty("error_description")
    private String errorDescription;
    @JsonProperty("unionid")
    private String unionid;
    @JsonProperty("client_id")
    private String clientId;
}
