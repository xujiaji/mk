package com.github.xujiaji.mk.sms.front.payload;

import lombok.Data;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

/**
 * @author jiajixu
 * @date 2020-05-27 17:55
 */
@Data
public class SmsCondition {
    /**
     * type:1普通短信，2注册，3登录，4信息变更，5修改绑定手机号
     */
    @NotNull(message = "短信类型不能为NULL")
    @Range(min = 0, max = 5, message = "没有该类型的短信")
    private Integer type;
    @NotBlank(message = "手机号不能为空")
    @Pattern(regexp = "1[0-9]{10}", message = "手机号校验失败")
    private String mobile;
}
