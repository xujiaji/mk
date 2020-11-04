package com.github.xujiaji.mk.user.front.service;

import cn.hutool.core.util.StrUtil;
import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONUtil;
import com.aliyuncs.CommonRequest;
import com.aliyuncs.CommonResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.xujiaji.mk.common.base.Consts;
import com.github.xujiaji.mk.common.base.Status;
import com.github.xujiaji.mk.common.entity.MkUser;
import com.github.xujiaji.mk.common.exception.RequestActionException;
import com.github.xujiaji.mk.common.exception.StatusException;
import com.github.xujiaji.mk.common.service.IPasswordService;
import com.github.xujiaji.mk.common.service.impl.MkCommonServiceImpl;
import com.github.xujiaji.mk.common.util.RedisUtil;
import com.github.xujiaji.mk.user.entity.MkSms;
import com.github.xujiaji.mk.user.front.dto.AliSmsDTO;
import com.github.xujiaji.mk.user.front.dto.QQLoginDTO;
import com.github.xujiaji.mk.user.front.dto.WXLoginDTO;
import com.github.xujiaji.mk.user.front.dto.WXMiniLoginDTO;
import com.github.xujiaji.mk.user.front.payload.*;
import com.github.xujiaji.mk.user.front.util.WXBizDataCrypt;
import com.github.xujiaji.mk.user.service.impl.MkSmsServiceImpl;
import com.github.xujiaji.mk.user.service.impl.MkUserIdNumberServiceImpl;
import com.github.xujiaji.mk.user.service.impl.MkUserServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author jiajixu
 * @date 2020/10/26 10:47
 */
@Slf4j
@RequiredArgsConstructor
@Service
public class MkAuthUserService extends MkUserServiceImpl {

    /**
     * QQ返回数据提取正则
     */
    private static Pattern QQ_CALLBACK_PATTERN = Pattern.compile("(?<=\\()([\\s\\S]+)(?=\\))", Pattern.CASE_INSENSITIVE);
    private final MkUserIdNumberServiceImpl userIdNumberService;
    private final IPasswordService passwordService;
    private final MkCommonServiceImpl mkCommonService;
    private final RedisUtil redisUtil;
    private final MkSmsServiceImpl mkSmsService;

    /**
     * 创建用户
     * @return 用户数据
     */
    @Transactional(rollbackFor = Exception.class)
    public MkUser createUser(@Nullable String qqId, @Nullable String wxId, @Nullable String wxMiniOpenId, @Nullable String iOSId, @Nullable String mobile, @Nullable String password) {
        val mkUserIdNumber = userIdNumberService.newNormalIdNumber();
        MkUser user = new MkUser();
        user.setNo(mkUserIdNumber.getId().toString());
        user.setUsername("mk" + user.getNo());
        user.setPhone(mobile);
        user.setPassword(passwordService.encode(password));
        user.setQqId(qqId);
        user.setWxId(wxId);
        user.setWxMiniOpenId(wxMiniOpenId);
        user.setIosId(iOSId);
        add(user);

        mkUserIdNumber.setState(Consts.ENABLE);
        userIdNumberService.editById(mkUserIdNumber);
        return user;
    }

    /**
     * 通过QQ登录或绑定QQ
     */
    public MkUser authQQ(ThirdLoginCondition request, @Nullable MkUser currentUser) {
        String c = HttpUtil.get("https://graph.qq.com/oauth2.0/me?unionid=1&access_token=" + request.getTokenId());
        Matcher matcher = QQ_CALLBACK_PATTERN.matcher(c);
        log.info("QQ: {}", c);

        if (matcher.find()) {
            val qqLoginModel = JSONUtil.toBean(matcher.group(), QQLoginDTO.class);
            if (StrUtil.isBlank(qqLoginModel.getUnionid())) {
                throw new RequestActionException("没有获取到openid");
            }
            val selectUser = baseMapper.selectOne(new QueryWrapper<MkUser>().eq("qq_id", qqLoginModel.getUnionid()));
            if (currentUser == null) {
                // 用三方登录的时候
                if (selectUser != null) {
                    return selectUser;
                }
                return createUser(qqLoginModel.getUnionid(), null, null, null, null, null);
            } else {
                // 绑定三方登录的时候
                if (selectUser == null) {
                    currentUser.setQqId(qqLoginModel.getUnionid());
                    return currentUser;
                } else {
                    throw new RequestActionException("当前QQ已绑定其它账号");
                }
            }
        } else {
            throw new RuntimeException("没有得到QQ登录验证信息");
        }
    }

    /**
     * 通过微信登录或绑定微信
     */
    public MkUser authWeiXin(ThirdLoginCondition request, MkUser currentUser) {

        Map<String, Object> query = new HashMap<>();
        query.put("openid", mkCommonService.valueByKey(Consts.ConfigKey.wxAppId));
        query.put("access_token", request.getTokenId());

        val wxResult = HttpUtil.get("https://api.weixin.qq.com/sns/userinfo", query);
        log.info("微信: {}", wxResult);
        val wxLoginModel = JSONUtil.toBean(wxResult, WXLoginDTO.class);
        val selectUser = baseMapper.selectOne(new QueryWrapper<MkUser>().eq("wx_id", wxLoginModel.getUnionid()));

        if (currentUser == null) {
            // 用三方来登录的时候
            if (selectUser != null) {
                return selectUser;
            }
            return createUser(null, wxLoginModel.getUnionid(), null, null, null, null);
        } else {
            // 绑定三方登录的时候
            if (selectUser == null) {
                currentUser.setWxId(wxLoginModel.getUnionid());
                return currentUser;
            } else {
                throw new RequestActionException("当前微信已绑定其它账号");
            }
        }
    }

    public MkUser authMiniWeiXin(MiniWxLoginCondition request) {
        Map<String, Object> query = new HashMap<>();
        query.put("appid", mkCommonService.valueByKey(Consts.ConfigKey.wxMiniAppId));
        query.put("secret", mkCommonService.valueByKey(Consts.ConfigKey.wxMiniSecret));
        query.put("js_code", request.getJsCode());
        query.put("grant_type", "authorization_code");

        val wxResult = HttpUtil.get("https://api.weixin.qq.com/sns/jscode2session", query);
        log.info("微信小程序: {}", wxResult);
        val wxLoginModel = JSONUtil.toBean(wxResult, WXMiniLoginDTO.class);
        if (wxLoginModel.getErrcode() != null) {
            throw new RequestActionException("错误码：" + wxLoginModel.getErrcode() + "，信息：" + wxLoginModel.getErrmsg());
        }
        if (StrUtil.isBlank(wxLoginModel.getUnionid())) {
            redisUtil.putWxMiniSessionKey(request.getJsCode(), wxLoginModel.getSessionKey());
            return null;
        }
        return getUserDOByWxUnionId(wxLoginModel.getOpenid(), wxLoginModel.getUnionid());
    }

    private MkUser getUserDOByWxUnionId(String openId, String unionId) {
        val wxLoginUser = baseMapper.selectOne(new QueryWrapper<MkUser>().eq("wx_id", unionId));
        if (wxLoginUser != null) { // 如果是微信登录过的用户
            if (StrUtil.isEmpty(wxLoginUser.getWxMiniOpenId())) { // 如果没有更新过小程序ID
                wxLoginUser.setWxMiniOpenId(openId);
                editById(wxLoginUser);
            }
            return wxLoginUser;
        }
        return createUser(null, unionId, openId,null, null, null);
    }

    /**
     * 微信小程序认证登录（没有在APP上登录过的情况）
     * @param request
     * @return
     */
    @SneakyThrows
    public MkUser authMiniWeiXinByInfo(MiniWxInfoLoginCondition request) {
        final String sessionKey = redisUtil.getWxMiniSessionKey(request.getJsCode());
        if (StrUtil.isBlank(sessionKey)) {
            throw new RequestActionException("请先调用登录");
        }
        WXBizDataCrypt wxBizDataCrypt = new WXBizDataCrypt(mkCommonService.valueByKey(Consts.ConfigKey.wxMiniAppId), sessionKey);
        final val decrypt = wxBizDataCrypt.decrypt(request.getEncryptedData(), request.getIv());
        return getUserDOByWxUnionId(decrypt.getOpenId(), decrypt.getUnionId());
    }

    public MkUser authIOS(ThirdLoginCondition request, MkUser currentUser) {
        return null;
    }

    public void bindThirdLogin(Long userId, ThirdLoginCondition request) {
        MkUser user = new MkUser();
        user.setId(userId);
        switch (request.getType()) {
            case Consts.LoginType.QQ:
                user = authQQ(request, user);
                break;
            case Consts.LoginType.WX:
                user = authWeiXin(request, user);
                break;
            case Consts.LoginType.IOS:
                user = authIOS(request, user);
                break;
            default:
                throw new RuntimeException("没有这个登录类型");
        }
        editById(user);
    }

    public void unBindThirdLogin(String type, Long userId) {
        val user = baseMapper.selectById(userId);
        int num = (StrUtil.isNotBlank(user.getQqId()) ? 1 : 0)
                + (StrUtil.isNotBlank(user.getWxId()) ? 1 : 0)
                + (StrUtil.isNotBlank(user.getIosId()) ? 1 : 0)
                + (StrUtil.isNotBlank(user.getPhone()) ? 1 : 0);
        if (num == 1) {
            throw new RequestActionException("至少需要有一个绑定状态");
        }
        switch (Integer.parseInt(type)) {
            case Consts.LoginType.QQ:
                user.setQqId("");
                break;
            case Consts.LoginType.WX:
                user.setWxId("");
                user.setWxMiniOpenId("");
                break;
            case Consts.LoginType.IOS:
                user.setIosId("");
                break;
            default:
                throw new RuntimeException("没有这个登录类型");
        }
        editById(user);
    }

    public void sendSms(SmsCondition smsRequest) {
        String templateCodeKey = null;
        switch (smsRequest.getType()) {
            case Consts.Sms.NORMAL:
                templateCodeKey = Consts.ConfigKey.smsTemplateNormal;
                break;
            case Consts.Sms.REGISTER:
                if (baseMapper.isExistMobile(smsRequest.getMobile())) {
                    throw new StatusException(Status.ERROR_PHONE_REGISTERED);
                }
                templateCodeKey = Consts.ConfigKey.smsTemplateRegister;
                break;
            case Consts.Sms.LOGIN:
                templateCodeKey = Consts.ConfigKey.smsTemplateLogin;
                break;
            case Consts.Sms.MODIFY:
                templateCodeKey = Consts.ConfigKey.smsTemplateModify;
                break;
            case Consts.Sms.MODIFY_MOBILE:
                if (baseMapper.isExistMobile(smsRequest.getMobile())) {
                    throw new StatusException(Status.ERROR_PHONE_BOUND);
                }
                templateCodeKey = Consts.ConfigKey.smsTemplateModifyPhone;
                break;
            default:
                break;
        }

        // 生成验证码
        final String code = String.valueOf(new Random().nextInt(8999) + 1000);
        DefaultProfile profile = DefaultProfile.getProfile("cn-hangzhou", mkCommonService.valueByKey(Consts.ConfigKey.aliSmsKey), mkCommonService.valueByKey(Consts.ConfigKey.aliSmsSecret));
        IAcsClient client = new DefaultAcsClient(profile);
        CommonRequest request = new CommonRequest();
        request.setSysMethod(MethodType.POST);
        request.setSysDomain("dysmsapi.aliyuncs.com");
        request.setSysVersion("2017-05-25");
        request.setSysAction("SendSms");
        request.putQueryParameter("RegionId", "cn-hangzhou");
        request.putQueryParameter("PhoneNumbers", smsRequest.getMobile());
        request.putQueryParameter("SignName", mkCommonService.valueByKey(Consts.ConfigKey.aliSmsSignName));
        request.putQueryParameter("TemplateCode", mkCommonService.valueByKey(templateCodeKey));
        request.putQueryParameter("TemplateParam",
                String.format("{" +
                                "\"phone\": \"%s\", " +
                                "\"code\": \"%s\", " +
                                "\"product\": \"dsd\"}",
                        smsRequest.getMobile(), code));
        request.putQueryParameter("SmsUpExtendCode", "1234567");
        request.putQueryParameter("OutId", "yourOutId");
        CommonResponse response = null;
        try {
            response = client.getCommonResponse(request);
        } catch (ClientException e) {
            e.printStackTrace();
            throw new RequestActionException("验证码发送请求失败");
        }
        log.info("短信发送结果：{}", response.getData());
        val aliSmsModel = JSONUtil.toBean(response.getData(), AliSmsDTO.class);
        if (!"OK".equals(aliSmsModel.getCode())) {
            throw new RequestActionException("验证码发送失败");
        }
        val mkSms = new MkSms();
        mkSms.setCode(Integer.valueOf(code));
        mkSms.setMobile(smsRequest.getMobile());
        mkSms.setType(smsRequest.getType());
        mkSmsService.add(mkSms);
    }

    /**
     * 验证是否是有效的验证码
     * @param mobile 手机号
     * @param code 验证码
     * @param type 验证码类型
     * @return
     */
    private void checkValidSmsCode(String mobile, int code, int type) {
        if (type == Consts.Sms.REGISTER && baseMapper.isExistMobile(mobile)) {
            throw new StatusException(Status.ERROR_PHONE_REGISTERED);
        }
        if (!mkSmsService.isValid(mobile, type, code)) {
            throw new RequestActionException("验证码错误或已过期");
        }
    }

    /**
     * 用户手机号注册
     */
    @Transactional(rollbackFor = Exception.class)
    public MkUser registerByPhone(RegisterCondition register) {
        checkValidSmsCode(register.getMobile(), register.getCode(), Consts.Sms.REGISTER);
        return createUser(null, null, null, null, register.getMobile(), register.getPassword());
    }

    /**
     * 通过手机号获取用户信息
     */
    private MkUser getUserByMobile(String mobile) {
        val user = baseMapper.selectByPhone(mobile);
        if (user == null) {
            throw new RequestActionException("该账号不存在");
        }
        return user;
    }

    /**
     * 手机账号密码登录
     */
    public MkUser loginByMobile(MobileLoginCondition mobileLoginRequest) {
        MkUser mkUser = getUserByMobile(mobileLoginRequest.getMobile());
        if (StrUtil.isBlank(mkUser.getPassword())) {
            throw new RequestActionException("该账号还未设置过密码，请使用短信登录");
        }
        if (passwordService.matches(mobileLoginRequest.getPassword(), mkUser.getPassword())) {
            throw new RequestActionException("密码错误");
        }
        return mkUser;
    }

    /**
     * 手机验证码登录
     */
    public MkUser loginByMobileSms(MobileSmsLoginCondition mobileSmsLoginRequest) {
        checkValidSmsCode(mobileSmsLoginRequest.getMobile(), mobileSmsLoginRequest.getCode(), Consts.Sms.LOGIN);
        try { // 有账号
            return getUserByMobile(mobileSmsLoginRequest.getMobile());
        } catch (RequestActionException e) { // 没有账号
            return createUser(null, null, null, null, mobileSmsLoginRequest.getMobile(), null);
        }
    }

    /**
     * 忘记密码
     */
    public void forgetPassword(ForgetPasswordCondition request) {
        checkValidSmsCode(request.getMobile(), request.getCode(), Consts.Sms.MODIFY);
        if (!request.getNewPass().equals(request.getConfirmPass())) {
            throw new RequestActionException("两次密码不一致");
        }
        if (!baseMapper.isExistMobile(request.getMobile())) {
            throw new RequestActionException("当前手机号没有注册");
        }
        val mkUser = baseMapper.selectByPhone(request.getMobile());
        mkUser.setPassword(passwordService.encode(request.getNewPass()));
        editById(mkUser);
    }

    /**
     * 设置密码
     * @param userId 用户id
     * @param spr 设置密码的请求
     */
    public void setPassword(Long userId, SetPasswordCondition spr) {
        val mkUser = baseMapper.selectById(userId);
        if (StrUtil.isNotBlank(mkUser.getPassword())) {
            throw new RequestActionException("您已经有了密码，不可设置");
        }
        mkUser.setPassword(passwordService.encode(spr.getPassword()));
        editById(mkUser);
    }

    /**
     * 绑定手机号
     * @param userId 用户ID
     * @param request 绑定手机号请求
     */
    public void bindMobileAndPassword(Long userId, BindMobileCondition request) {
        checkValidSmsCode(request.getMobile(), request.getCode(), Consts.Sms.MODIFY_MOBILE);
        bindMobile(userId, request, true);
    }


    /**
     * 绑定手机号
     * @param userId 用户ID
     * @param request 绑定手机号请求
     * @param updatePassword 绑定手机号同时设置验证码
     */
    private void bindMobile(Long userId, BindMobileCondition request, boolean updatePassword) {
        if (baseMapper.isExistMobile(request.getMobile())) {
            throw new RequestActionException("您所绑定的手机号已有对应账户，请更换手机号或解绑后重试！");
        }
        val user = baseMapper.selectById(userId);
        user.setPhone(request.getMobile());
        if (updatePassword) {
            if (StrUtil.isBlank(request.getPassword())) {
                throw new RequestActionException("请输入新的登录密码");
            }
            user.setPassword(passwordService.encode(request.getPassword()));
        }
        editById(user);
    }

    /**
     * 修改绑定手机号
     * @param userId 用户ID
     * @param request 修改绑定手机号请求
     */
    public void changeMobile(Long userId, BindMobileCondition request) {
        checkValidSmsCode(request.getMobile(), request.getCode(), Consts.Sms.MODIFY_MOBILE);
        MkUser user = baseMapper.selectById(userId);
        if (StrUtil.isBlank(user.getPassword())) {
            throw new RequestActionException("没有密码无法修改，请先设置密码");
        }
        bindMobile(userId, request, false);
    }

    /**
     * 修改登录密码
     * @param userId 用户ID
     * @param mpr 修改密码的请求
     */
    public void modifyPassword(Long userId, ModifyPasswordCondition mpr) {
        val mkUser = baseMapper.selectById(userId);
        if (StrUtil.isBlank(mkUser.getPhone())) {
            throw new RequestActionException("当前账号未绑定手机号");
        }
        checkValidSmsCode(mkUser.getPhone(), mpr.getCode(), Consts.Sms.MODIFY);
        if (!mpr.getNewPass().equals(mpr.getConfirmPass())) {
            throw new RequestActionException("两次密码不一致哟，检查后重试");
        }
        mkUser.setPassword(passwordService.encode(mpr.getNewPass()));
        editById(mkUser);
    }
}
