package com.github.xujiaji.mk.user.front.vo;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author jiajixu
 * @date 2020/11/2 10:57
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class LoginSuccessVO extends UserVO {
    private String authorization;
    private String authorizationType;
}
