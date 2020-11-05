package com.github.xujiaji.mk.common.base;

/**
 *
 * 常量池
 */
public interface Consts {
    /**
     * 启用
     */
    Integer ENABLE = 1;
    /**
     * 禁用
     */
    Integer DISABLE = 0;

    /**
     * 正向
     */
    int POSITIVE = 1;

    /**
     * 反向
     */
    int NEGATIVE = 0;

    /**
     * 页面
     */
    Integer PAGE = 1;

    /**
     * 按钮
     */
    Integer BUTTON = 2;

    /**
     * 星号
     */
    String SYMBOL_STAR = "*";

    /**
     * 邮箱符号
     */
    String SYMBOL_EMAIL = "@";

    /**
     * 默认当前页码
     */
    Integer DEFAULT_CURRENT_PAGE = 1;

    /**
     * 默认每页条数
     */
    Integer DEFAULT_PAGE_SIZE = 10;

    /**
     * 匿名用户 用户名
     */
    String ANONYMOUS_NAME = "匿名用户";

    /**
     * 数据库中配置的key
     */
    interface ConfigKey {
        /**
         * 基础文件存放路径
         */
        String basePath = "basePath";

        /**
         * IP城市数据库路径
         */
        String ipCityDBPath = "ipCityDBPath";

        /**
         * 微信appId
         */
        String wxAppId = "wxAppId";

        /**
         * 微信支付key
         */
        String wxPayKey = "wxPayKey";

        /**
         * 微信secret
         */
        String wxSecret = "wxSecret";

        /**
         * 微信小程序appid
         */
        String wxMiniAppId = "wxMiniAppId";

        /**
         * 微信小程序secret
         */
        String wxMiniSecret = "wxMiniSecret";

        /**
         * 短信模版-普通
         */
        String smsTemplateNormal = "smsTemplateNormal";

        /**
         * 短信模版-注册
         */
        String smsTemplateRegister = "smsTemplateRegister";

        /**
         * 短信模版-登录
         */
        String smsTemplateLogin = "smsTemplateLogin";

        /**
         * 短信模版-信息变更
         */
        String smsTemplateModify = "smsTemplateModify";

        /**
         * 短信模版-手机号变更
         */
        String smsTemplateModifyPhone = "smsTemplateModifyPhone";

        /**
         * 阿里云短信key
         */
        String aliSmsKey = "aliSmsKey";

        /**
         * 阿里云短信secret
         */
        String aliSmsSecret = "aliSmsSecret";

        /**
         * 阿里云短信sign name
         */
        String aliSmsSignName = "aliSmsSignName";
    }

    /**
     * 权限类型
     */
    interface PermissionType {
        /**
         * 页面
         */
        int PAGE = 1;

        /**
         * 按钮
         */
        int BOTTOM = 2;
    }

    /**
     * 正则表达式
     */
    interface Regexp {
        /**
         * 手机号
         */
        String PHONE = "1[0-10]{10}";
    }

    /**
     * 登录类型
     * 1.QQ登录；2.微信登录；3.手机密码登录；4.手机验证码登录；5.邮箱登录；6.iOS登录；7.用户名密码登录；8.微信小程序；9.刷新登录
     */
    interface LoginType {
        int QQ = 1;
        int WX = 2;
        int PHONE_PASSWORD = 3;
        int PHONE_SMS = 4;
        int EMAIL = 5;
        int IOS = 6;
        int USERNAME = 7;
        int WX_MINI = 8;
        int REFRESH = 9;
    }

    interface Sms {
        /**
         * 普通短信
         */
        int NORMAL = 1;
        /**
         * 注册短信
         */
        int REGISTER = 2;
        /**
         * 登录短信
         */
        int LOGIN = 3;
        /**
         * 信息变更短信
         */
        int MODIFY = 4;
        /**
         * 修改绑定手机号
         */
        int MODIFY_MOBILE = 5;
    }
}
