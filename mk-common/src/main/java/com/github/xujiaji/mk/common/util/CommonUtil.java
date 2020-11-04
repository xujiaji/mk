package com.github.xujiaji.mk.common.util;

import cn.hutool.core.util.StrUtil;
import com.github.xujiaji.mk.common.base.Consts;
import org.springframework.stereotype.Component;

/**
 * @author jiajixu
 * @date 2020/11/4 10:52
 */
@Component
public class CommonUtil {

    /**
     * 脱敏手机号
     */
    public String hidePhone(String phone) {
        if (StrUtil.isBlank(phone)) {
            return phone;
        }
        return StrUtil.hide(phone, 3, 7);
    }

    /**
     * 脱敏邮箱号
     */
    public String hideEmail(String email) {
        if (StrUtil.isBlank(email)) {
            return email;
        }
        return StrUtil.hide(email, 1, StrUtil.indexOfIgnoreCase(email, Consts.SYMBOL_EMAIL));
    }
}
