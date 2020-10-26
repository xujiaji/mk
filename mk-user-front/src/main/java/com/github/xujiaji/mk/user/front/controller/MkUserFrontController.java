package com.github.xujiaji.mk.user.front.controller;

import com.github.xujiaji.mk.common.base.BaseController;
import com.github.xujiaji.mk.user.front.service.MkUserFrontService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 用户 front 控制器
 * @author jiajixu
 * @date 2020/10/26 10:38
 */
@RestController
@RequestMapping("/user-front")
public class MkUserFrontController extends BaseController {

    @Autowired
    private MkUserFrontService mkUserFrontService;

}
