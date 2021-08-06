package com.github.xujiaji.mk.common.payload;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotNull;

/**
 * @author jiajixu
 * @date 2020/11/13 12:46
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class UserPageCondition extends PageCondition {

    /**
     * 用户id
     */
    @NotNull(message = "请传入用户id")
    private Long userId;
}
