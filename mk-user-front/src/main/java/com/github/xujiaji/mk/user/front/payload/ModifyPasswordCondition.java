package com.github.xujiaji.mk.user.front.payload;

import lombok.Data;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @author jiajixu
 * @date 2020-05-29 15:48
 */
@Data
public class ModifyPasswordCondition {

    /**
     * 新密码
     */
    @NotBlank(message = "新密码不能为空")
    private String newPass;

    @NotNull(message = "验证码不能为空")
    @Range(min = 1000, max = 9999, message = "请输入正确的验证码")
    private Integer code;

    /**
     * 确认密码
     */
    @NotBlank(message = "确认密码不能为空")
    private String confirmPass;
}
