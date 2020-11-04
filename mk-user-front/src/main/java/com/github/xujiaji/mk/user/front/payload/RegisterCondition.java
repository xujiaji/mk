package com.github.xujiaji.mk.user.front.payload;

import lombok.Data;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

/**
 * @author jiajixu
 * @date 2020-05-28 09:48
 */
@Data
public class RegisterCondition {

    /**
     * 手机号
     */
    @NotBlank(message = "手机号不能为空")
    @Pattern(regexp = "1[0-9]{10}", message = "手机号校验失败")
    private String mobile;

    /**
     * 密码
     */
    @NotBlank(message = "密码不能为空")
    private String password;

    /**
     * 验证码
     */
    @NotNull(message = "验证码不能为空")
    @Range(min = 1000, max = 9999, message = "请输入正确的验证码")
    private Integer code;

    /**
     * 邀请码（可选）
     */
    private String inviteCode;
}
