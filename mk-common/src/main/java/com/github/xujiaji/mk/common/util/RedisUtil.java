package com.github.xujiaji.mk.common.util;

import com.github.xujiaji.mk.common.vo.PageVO;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.*;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * <p>
 * Redis工具类
 * </p>
 */
@Component
@Slf4j
public class RedisUtil {
    public static final String GUID_VERIFY = "guid_verify";

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    /**
     * 分页获取指定格式key，使用 scan 命令代替 keys 命令，在大数据量的情况下可以提高查询效率
     *
     * @param patternKey  key格式
     * @param currentPage 当前页码
     * @param pageSize    每页条数
     * @return 分页获取指定格式key
     */
    public PageVO<String> findKeysForPage(String patternKey, long currentPage, long pageSize) {
        ScanOptions options = ScanOptions.scanOptions()
                .match(patternKey)
                .build();
        RedisConnectionFactory factory = stringRedisTemplate.getConnectionFactory();
        RedisConnection rc = factory.getConnection();
        Cursor<byte[]> cursor = rc.scan(options);

        List<String> result = Lists.newArrayList();

        long tmpIndex = 0;
        long startIndex = (currentPage - 1) * pageSize;
        long end = currentPage * pageSize;
        while (cursor.hasNext()) {
            String key = new String(cursor.next());
            if (tmpIndex >= startIndex && tmpIndex < end) {
                result.add(key);
            }
            tmpIndex++;
        }

        try {
            cursor.close();
            RedisConnectionUtils.releaseConnection(rc, factory, false);
        } catch (Exception e) {
            log.warn("Redis连接关闭异常，", e);
        }
        return PageVO.create(result, currentPage, pageSize, tmpIndex);
    }


    /**
     * 设置session和验证码
     */
    public void setGuidAndVerifyCode(String guid, String verifyCode) {
        val sv = stringRedisTemplate.opsForHash();
        sv.put(GUID_VERIFY, guid, verifyCode);
        stringRedisTemplate.expire(GUID_VERIFY, 1, TimeUnit.MINUTES);
    }

    /**
     * 通过guid获取验证码
     * @param guid
     * @return
     */
    public String getVerifyCodeByGuid(String guid) {
        HashOperations<String, String, String> sv = stringRedisTemplate.opsForHash();
        if (sv.hasKey(GUID_VERIFY, guid)) {
            log.info("从redis获取验证码");
            return sv.get(GUID_VERIFY, guid);
        }
        return null;
    }

    /**
     * 删除 Redis 中的某个key
     *
     * @param key 键
     */
    public void delete(String key) {
        stringRedisTemplate.delete(key);
    }

    /**
     * 批量删除 Redis 中的某些key
     *
     * @param keys 键列表
     */
    public void delete(Collection<String> keys) {
        stringRedisTemplate.delete(keys);
    }
}
