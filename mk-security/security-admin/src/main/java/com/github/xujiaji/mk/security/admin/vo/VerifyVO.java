package com.github.xujiaji.mk.security.admin.vo;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author jiajixu
 * @date 2020/6/22 10:31
 */
@Data
@AllArgsConstructor
public class VerifyVO {
    private String verify;
    private String imageBase64;
}
