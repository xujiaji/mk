package com.github.xujiaji.mk.pay.front.pay.wx;

import cn.hutool.core.io.IoUtil;
import com.github.wxpay.sdk.WXPayConfig;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ClassPathResource;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

public class MkWXConfig implements WXPayConfig {

    private final String appId;
    private final String mchId;
    private final String key;

    private byte[] certData;

    public MkWXConfig(String appId, String mchId, String key) {
        this.appId = appId;
        this.mchId = mchId;
        this.key = key;
        ClassPathResource classPathResource = new ClassPathResource("weixin/apiclient_cert.p12");
        //获取文件流
        InputStream certStream = null;
        try {
            certStream = classPathResource.getInputStream();
            byte[] bytes = new byte[certStream.available()];
            certStream.read(bytes);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            IoUtil.close(certStream);
        }

    }

    @Override
    public String getAppID() {
        return appId;
    }

    @Override
    public String getMchID() {
        return mchId;
    }

    @Override
    public String getKey() {
        return key;
    }

    @Override
    public InputStream getCertStream() {
        ByteArrayInputStream certBis = new ByteArrayInputStream(this.certData);
        return certBis;
    }

    @Override
    public int getHttpConnectTimeoutMs() {
        return 8000;
    }

    @Override
    public int getHttpReadTimeoutMs() {
        return 10000;
    }
}