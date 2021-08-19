package com.github.xujiaji.mk.security.dto;

import com.github.xujiaji.mk.security.entity.MkSecRole;
import com.github.xujiaji.mk.security.entity.MkSecUser;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * @author jiajixu
 * @date 2021/8/19 22:05
 */
@Getter
@Setter
public class MkSecUserDTO extends MkSecUser {
    private List<MkSecRole> roles;
}
