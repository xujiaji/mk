package com.github.xujiaji.mk.pay.front.pay.qq;

import com.github.xujiaji.mk.common.entity.MkUser;
import com.github.xujiaji.mk.pay.front.pay.BaseMkPay;
import com.github.xujiaji.mk.pay.front.pay.PayRequest;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * @author jiajixu
 * @date 11/30/20 3:19 PM
 */
@Component
public class MkQQPay extends BaseMkPay {

    @Override
    public Map<String, Object> pay(MkUser user, PayRequest request) throws Exception {
        return null;
    }

    @Override
    public Object notify(String notify, HttpServletRequest request) {
        return null;
    }
}
