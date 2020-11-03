package com.github.xujiaji.mk.user.payload;

import com.github.xujiaji.mk.common.payload.PageCondition;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotNull;

/**
 * @author jiajixu
 * @date 2020/11/3 15:22
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class LogPageCondition extends PageCondition {
    /**
     * 用户类型：0所有，1用户，2管理员
     */
    @NotNull(message = "请传入用户类型")
    private Integer userType;

    /**
     * 通过IP筛选登录日志
     */
    private String loginIp;

    /**
     * 通过用户名筛选登录日志
     */
    private String username;

    /**
     * 通过位置筛选登录日志
     */
    private String location;

    /**
     * 通过设备塞选登录日志
     */
    private String device;
}
