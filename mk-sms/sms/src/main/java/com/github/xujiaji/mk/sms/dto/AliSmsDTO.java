package com.github.xujiaji.mk.sms.dto;

import lombok.Data;

/**
 * @author jiajixu
 * @date 2020-05-27 22:30
 */
@Data
public class AliSmsDTO {
    private String Message;
    private String RequestId;
    private String BizId;
    private String Code;
}
