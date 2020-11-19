package com.github.xujiaji.mk.user.admin.payload;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotNull;

/**
 * @author jiajixu
 * @date 2020/11/19 11:46
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class AppVersionEditCondition extends AppVersionAddCondition {
    /**
     * 主键
     */
    @NotNull(message = "id不能为空")
    private Long id;
}
