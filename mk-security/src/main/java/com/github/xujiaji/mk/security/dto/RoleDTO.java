package com.github.xujiaji.mk.security.dto;

import com.github.xujiaji.mk.security.entity.MkSecPermission;
import com.github.xujiaji.mk.security.entity.MkSecRole;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

/**
 * @author jiajixu
 * @date 2020/11/16 17:41
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class RoleDTO extends MkSecRole {
    private List<MkSecPermission> permissions;
}
