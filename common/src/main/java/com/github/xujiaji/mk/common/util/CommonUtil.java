package com.github.xujiaji.mk.common.util;

import cn.hutool.core.util.StrUtil;
import com.github.xujiaji.mk.common.base.Consts;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.LocalDateTime;

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

    public String getShortTime(LocalDateTime time) {
        String shortString = null;
        if (time == null) {
            return null;
        }
        long delTime = Duration.between(time, LocalDateTime.now()).toMillis() / 1000L;
        if (delTime > 365 * 24 * 60 * 60) {
            shortString = (int) (delTime / (365 * 24 * 60 * 60)) + "年前";
        } else if (delTime > 24 * 60 * 60) {
            shortString = (int) (delTime / (24 * 60 * 60)) + "天前";
        } else if (delTime > 60 * 60) {
            shortString = (int) (delTime / (60 * 60)) + "小时前";
        } else if (delTime > 60) {
            shortString = (int) (delTime / (60)) + "分钟前";
        } else if (delTime > 1) {
            shortString = delTime + "秒前";
        } else {
            shortString = "1秒前";
        }
        return shortString;
    }
}