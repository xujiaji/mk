package com.github.xujiaji.mk.security.admin.payload;

import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * 权限添加约束
 * @author jiajixu
 * @date 2020/10/28 23:20
 */
@Data
public class PermissionAddCondition {
    /**
     * 权限名字
     */
    @NotNull(message = "权限名字不能为空")
    private String name;

    /**
     * 权限父级id
     */
    @NotNull(message = "父级ID不能为空，如果是顶级请传入0")
    private Long parentId;

    /**
     * 权限类型，页面-1，按钮-2
     */
    @NotNull(message = "类型不能为空")
    private Integer type;

    /**
     * 类型为页面1时，代表前端路由地址；类型为按钮2时，代表后端接口地址
     */
    @NotNull(message = "请输入地址")
    private String path;

    /**
     * 前端所需元件
     */
    private String component;

    /**
     * 权限表达式
     */
    @NotNull(message = "请输入权限表达式")
    private String permission;

    /**
     * 后端接口访问方式(如果是接口时需要写)，请求方式如：GET POST PUT 等
     */
    private String method;

    /**
     * 排序
     */
    @NotNull(message = "排序不能为空")
    private Integer sort;


}
