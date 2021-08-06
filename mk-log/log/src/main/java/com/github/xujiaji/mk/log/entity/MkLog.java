package com.github.xujiaji.mk.log.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.github.xujiaji.mk.common.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 日志记录
 * </p>
 *
 * @author xujiaji
 * @since 2020-10-26
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("mk_log")
public class MkLog extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 创建者id
     */
    private Long userId;

    /**
     * 日志内容
     */
    private String content;

    /**
     * 日志类型
     */
    private Integer type;

    /**
     * user-agent
     */
    private String ua;

    /**
     * ip地址
     */
    private String ip;

    /**
     * 请求方式
     */
    private String method;

    /**
     * 头部信息
     */
    private String header;

    /**
     * 参数
     */
    private String param;

    /**
     * 路径
     */
    private String path;

    /**
     * 位置
     */
    private String location;

}
