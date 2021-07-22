package com.github.xujiaji.mk.common.payload;

import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

/**
 * @author jiajixu
 * @date 2020/10/30 17:19
 */
@Data
public class AdminStatusChangeCondition {
    /**
     * 管理员secUserId
     */
    @NotNull(message = "管理员secUserId不能为空")
    private Long secUserId;

    /**
     * 管理员当前是否启用。1启用，0禁止
     */
    @NotNull(message = "状态不能为空")
    @Pattern(regexp = "[01]", message = "1启用，0禁止。没有其它状态了")
    private String status;
}
