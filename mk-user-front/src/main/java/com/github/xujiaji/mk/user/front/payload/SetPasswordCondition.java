package com.github.xujiaji.mk.user.front.payload;

import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @author jiajixu
 * @date 2020-06-01 11:30
 */
@Data
public class SetPasswordCondition {
    /**
     * 密码
     */
    @NotBlank(message = "密码不能为空")
    private String password;
}
