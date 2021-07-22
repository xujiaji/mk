package com.github.xujiaji.mk.security.admin.vo;

import com.github.xujiaji.mk.security.entity.MkSecPermission;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author jiajixu
 * @date 2020/11/9 18:39
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class PermissionVO extends MkSecPermission {

    private PermissionMetaVO meta;

}



