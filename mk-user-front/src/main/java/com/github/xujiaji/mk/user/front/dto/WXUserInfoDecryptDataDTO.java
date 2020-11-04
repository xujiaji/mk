package com.github.xujiaji.mk.user.front.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author jiajixu
 * @date 2020/8/24 14:54
 */
@NoArgsConstructor
@Data
public class WXUserInfoDecryptDataDTO {

    /**
     * openId : oTEXG5FtAc3NbD3qJdSfqfzzZmv4
     * nickName : 一越飞鸿
     * gender : 1
     * language : zh_CN
     * city : Yubei
     * province : Chongqing
     * country : China
     * avatarUrl : https://thirdwx.qlogo.cn/mmopen/vi_32/Iicg2YcnWu7r33L1S7yiadL9bBPcborTqklno6zA9ibeA4qhvNut7JTvFdmUOiaFRu0siabazF32NY6lcicIGHNxqia4w/132
     * unionId : oECqewh44gVNXiFP9zy_LpR2hEv0
     * watermark : {"timestamp":1598251727,"appid":"wxd66df81977047193"}
     */

    private String openId;
    private String nickName;
    private int gender;
    private String language;
    private String city;
    private String province;
    private String country;
    private String avatarUrl;
    private String unionId;
    private WatermarkBean watermark;

    @NoArgsConstructor
    @Data
    public static class WatermarkBean {
        /**
         * timestamp : 1598251727
         * appid : wxd66df81977047193
         */

        private int timestamp;
        private String appid;
    }
}
