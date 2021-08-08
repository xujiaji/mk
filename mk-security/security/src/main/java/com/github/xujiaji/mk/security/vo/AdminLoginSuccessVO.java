package com.github.xujiaji.mk.security.vo;

import lombok.Builder;
import lombok.Data;

/**
 * @author jiajixu
 * @date 2020/11/2 10:57
 */
@Data
@Builder
public class AdminLoginSuccessVO {
    private String authorization;
    private String authorizationType;
    private MkSecUserDetails user;
}
