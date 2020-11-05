package com.github.xujiaji.mk.user.front.vo;

import com.github.xujiaji.mk.user.entity.MkUserView;
import lombok.Builder;
import lombok.Data;

/**
 * @author jiajixu
 * @date 2020/11/2 10:57
 */
@Data
@Builder
public class LoginSuccessVO {
    private String authorization;
    private String authorizationType;
    private MkUserView user;
}
