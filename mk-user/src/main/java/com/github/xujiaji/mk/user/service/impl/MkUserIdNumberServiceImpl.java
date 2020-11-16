package com.github.xujiaji.mk.user.service.impl;

import cn.hutool.core.collection.ConcurrentHashSet;
import cn.hutool.core.map.MapUtil;
import com.github.xujiaji.mk.common.base.BaseServiceImpl;
import com.github.xujiaji.mk.user.entity.MkUserIdNumber;
import com.github.xujiaji.mk.user.mapper.MkUserIdNumberMapper;
import com.github.xujiaji.mk.user.service.IMkUserIdNumberService;
import lombok.val;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Set;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author xujiaji
 * @since 2020-11-04
 */
@Service
public class MkUserIdNumberServiceImpl extends BaseServiceImpl<MkUserIdNumberMapper, MkUserIdNumber> implements IMkUserIdNumberService {

    private final Set<Map.Entry<String, String>> goodRegex = MapUtil.<String, String>builder()
            .put("业务相关的号", "^\\d{2}(0[1-9]|1[0-2])(0[1-9]|[12][0-9]|3[01])$")
            .put("业务相关的号", "^\\d*(1688|2688|2088|2008|5188|10010|10001|666|888|668|686|688|866|868|886|999)\\d*$")
            .put("AAABBB", "^\\d*(\\d)\\1\\1(\\d)\\2\\2\\d*$")
            .put("多重复号", "^\\d*(\\d)\\1{2,}\\d*$")
            .put("镜子号", "^(<a>\\d)(\\d)(\\d)\\1\\2\\3$")
            .put("镜子号", "^(\\d)(\\d)(\\d)\\3\\2\\1$")
            .put("AABB", "^\\d*(\\d)\\1(\\d)\\2\\d*$")
            .put("ABABAB", "^(\\d)(\\d)\\1\\2\\1\\2\\1\\2$")
            .put("ABCABC", "^(\\d)(\\d)(\\d)\\1\\2\\3$")
            .put("ABBABB", "^(\\d)(\\d)\\2\\1\\2\\2$")
            .put("AABAAB", "^(\\d)\\1(\\d)\\1\\1\\2$")
            .put("递增或者递减号", "(?:(?:0(?=1)|1(?=2)|2(?=3)|3(?=4)|4(?=5)|5(?=6)|6(?=7)|7(?=8)|8(?=9)|9(?=0)){2,}|(?:0(?=9)|9(?=8)|8(?=7)|7(?=6)|6(?=5)|5(?=4)|4(?=3)|3(?=2)|2(?=1)|1(?=0)){2,})\\d")
            .build().entrySet();
    private final Set<Long> allIdNumber = new ConcurrentHashSet<>();

    private MkUserIdNumber nextId(Long lastId) {
        if (allIdNumber.isEmpty()) { // 将所有ID放进去，用于判断是否添加过
            allIdNumber.addAll(baseMapper.selectAllIdNumber());
        }
        if (lastId == null) {
            lastId = 100000L;
        }
        val newId = lastId + 1L;
        if (allIdNumber.contains(newId)) {
            return nextId(newId);
        }
        allIdNumber.add(lastId);
        val newIdNumber = new MkUserIdNumber();
        newIdNumber.setId(newId);
        val first = goodRegex.stream().filter(p -> String.valueOf(newId).matches(p.getValue()))
                .findFirst();
        if (first.isPresent()) {
            newIdNumber.setGood(first.get().getKey());
            add(newIdNumber);
            return nextId(newId);
        } else {
            add(newIdNumber);
            return newIdNumber;
        }
    }

    @Override
    public MkUserIdNumber newNormalIdNumber() {
        val mkUserIdNumber = baseMapper.selectLastIdNumber();
        return nextId(mkUserIdNumber == null ? null : mkUserIdNumber.getId());
    }
}
