package com.github.xujiaji.mk.common.util;

import cn.hutool.extra.servlet.ServletUtil;
import com.github.xujiaji.mk.common.base.Consts;
import com.github.xujiaji.mk.common.service.impl.MkCommonServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.lionsoul.ip2region.DbConfig;
import org.lionsoul.ip2region.DbSearcher;
import org.lionsoul.ip2region.Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.io.File;

/**
 * IP工具
 */
@RequiredArgsConstructor
@Component
public class IPUtil {

    private final String[] IP_HEADER_CANDIDATES = {
            "X-Forwarded-For",
            "Proxy-Client-IP",
            "WL-Proxy-Client-IP",
            "HTTP_X_FORWARDED_FOR",
            "HTTP_X_FORWARDED",
            "HTTP_X_CLUSTER_CLIENT_IP",
            "HTTP_CLIENT_IP",
            "HTTP_FORWARDED_FOR",
            "HTTP_FORWARDED",
            "HTTP_VIA",
            "REMOTE_ADDR"
    };

    private final MkCommonServiceImpl mkCommonService;

    //查询算法
    //DbSearcher.BINARY_ALGORITHM //Binary
    //DbSearcher.MEMORY_ALGORITYM //Memory
    private final int algorithm = DbSearcher.BTREE_ALGORITHM; //B-tree
    private DbSearcher searcher;

    private void init() {
        if (searcher != null) {
            return;
        }
        synchronized (IPUtil.class) {
            if (searcher == null) {
                val ipCityDBPath = mkCommonService.valueByKey(Consts.ConfigKey.ipCityDBPath);
                File file = new File(ipCityDBPath);
                if (!file.exists()) {
                    System.err.println("Error: Invalid ip2region.db file");
                }
                try {
                    DbConfig config = new DbConfig();
                    searcher = new DbSearcher(config, ipCityDBPath);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public synchronized String getCityInfo(String ip) {
        init();
        if (!Util.isIpAddress(ip)) {
            System.out.println("Error: Invalid ip address");
        }

        if (searcher == null) {
            return "IP地址数据库初始化错误";
        }

        try {
            switch (algorithm) {
                case DbSearcher.BTREE_ALGORITHM:
                    return searcher.btreeSearch(ip).getRegion();
                case DbSearcher.BINARY_ALGORITHM:
                    return searcher.binarySearch(ip).getRegion();
                case DbSearcher.MEMORY_ALGORITYM:
                    return searcher.memorySearch(ip).getRegion();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public String getClientIpAddress(HttpServletRequest request) {
        return ServletUtil.getClientIP(request, IP_HEADER_CANDIDATES);
    }

    public String getClientIpAddress() {
        if (RequestContextHolder.getRequestAttributes() == null) {
            return "0.0.0.0";
        }
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        return getClientIpAddress(request);
    }

}