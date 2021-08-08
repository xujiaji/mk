package com.github.xujiaji.mk.security.admin.service;

import cn.hutool.core.util.StrUtil;
import com.github.xujiaji.mk.common.base.Consts;
import com.github.xujiaji.mk.common.payload.PageCondition;
import com.github.xujiaji.mk.common.service.IUserInfoService;
import com.github.xujiaji.mk.common.util.RedisUtil;
import com.github.xujiaji.mk.common.vo.PageVO;
import com.github.xujiaji.mk.security.config.JwtConfig;
import com.github.xujiaji.mk.security.entity.MkSecUser;
import com.github.xujiaji.mk.security.mapper.MkSecUserMapper;
import com.github.xujiaji.mk.security.util.SecurityUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 监控 Service
 * </p>
 */
@Slf4j
@RequiredArgsConstructor
@Service
public class AdminMonitorService {
    private final RedisUtil redisUtil;
    private final MkSecUserMapper mkSecUserMapper;
    private final IUserInfoService userInfoService;
    private final JwtConfig jwtConfig;

    /**
     * 在线用户分页列表
     *
     * @param pageCondition 分页参数
     * @return 在线用户分页列表
     */
    public PageVO<MkSecUser> onlineUser(PageCondition pageCondition) {
        PageVO<String> keys = redisUtil.findKeysForPage(jwtConfig.getRedisJwtKeyPrefix() + Consts.SYMBOL_STAR, pageCondition.getPage(), pageCondition.getSize());
        List<String> rows = keys.getList();

        // 根据 redis 中键获取用户名列表
        List<String> usernameList = rows.stream()
                .map(s -> StrUtil.subAfter(s, jwtConfig.getRedisJwtKeyPrefix(), true))
                .collect(Collectors.toList());
        // 根据用户名查询用户信息
        List<MkSecUser> userList = mkSecUserMapper.selectBatchIds(usernameList);

        userList.forEach(user -> {
            user.setPhone(StrUtil.hide(user.getPhone(), 3, 7));
            user.setEmail(StrUtil.hide(user.getEmail(), 1, StrUtil.indexOfIgnoreCase(user.getEmail(), Consts.SYMBOL_EMAIL)));
            user.setPassword(null);
        });
        return PageVO.create(userList, pageCondition.getPage(), pageCondition.getSize(), keys.getTotal());
    }

    /**
     * 踢出在线用户
     *
     * @param names 用户名列表
     */
    public void kickout(List<String> names) {
        // 清除 Redis 中的 JWT 信息
        List<String> redisKeys = names.parallelStream()
                .map(s -> jwtConfig.getRedisJwtKeyPrefix() + s)
                .collect(Collectors.toList());
        redisUtil.delete(redisKeys);

        // 获取当前用户名
        String currentUsername = SecurityUtil.getCurrentUsername();
        names.parallelStream()
                .forEach(name -> {
                    // TODO: 通知被踢出的用户已被当前登录用户踢出，
                    //  后期考虑使用 websocket 实现，具体伪代码实现如下。
                    //  String message = "您已被用户【" + currentUsername + "】手动下线！";
                    log.debug("用户【{}】被用户【{}】手动下线！", name, currentUsername);
                });
    }
}
