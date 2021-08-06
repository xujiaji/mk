package com.github.xujiaji.mk.common;

import cn.hutool.crypto.SecureUtil;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.util.Lists;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * @author jiajixu
 * @date 2020/11/6 15:10
 */
@Slf4j
public class CommonTests {

    @Test
    void testSign() {
        List<String> ignoreParams = Lists.newArrayList("file");
        String timestamp = "1604646960567";
        Map<String, String> params = new HashMap<>();
        params.put("type", "1");

        String values = "";
        if (!params.isEmpty()) {
            Optional<String> optional = params.keySet()
                    .stream()
                    .filter(s -> !ignoreParams.contains(s))
                    .sorted()
                    .map(params::get)
                    .reduce((s1, s2) -> s1 + s2);
            if (optional.isPresent()) {
                values = optional.get();
                log.info("values = {}", values);
            }
        }
        String s = SecureUtil.md5(values + SecureUtil.md5(timestamp.substring(0, 8)) + timestamp);
        log.info("sign = {}", s);
    }
}
