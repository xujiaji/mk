package com.github.xujiaji.mk.security.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.github.xujiaji.mk.common.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 权限表
 * </p>
 *
 * @author xujiaji
 * @since 2020-10-23
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("sec_permission")
public class SecPermission extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 权限名
     */
    private String name;

    /**
     * 类型为页面时，代表前端路由地址，类型为按钮时，代表后端接口地址
     */
    private String url;

    /**
     * 权限类型，页面-1，按钮-2
     */
    private Integer type;

    /**
     * 权限表达式
     */
    private String permission;

    /**
     * 后端接口访问方式
     */
    private String method;

    /**
     * 排序
     */
    private Integer sort;

    /**
     * 父级id
     */
    private Long parentId;


}
