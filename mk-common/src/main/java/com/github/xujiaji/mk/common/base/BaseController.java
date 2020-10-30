package com.github.xujiaji.mk.common.base;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.github.xujiaji.mk.common.payload.PageCondition;
import com.github.xujiaji.mk.common.vo.PageVO;
import lombok.val;
import org.springframework.validation.annotation.Validated;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Controller 基类
 * @author jiajixu
 * @date 2020/10/22 16:57
 */
@Validated
public class BaseController {

    /**
     * 将请求的page信息转换成mybatis-plus的page模型
     */
    protected <T> Page<T> mapPage(PageCondition pc) {
        return new Page<>(pc.getPage(), pc.getSize());
    }

    /**
     * 将mybatis-plus的page数据转换成response数据模型
     */
    private <T> PageVO<T> responsePage(IPage<T> page) {
        val pv = new PageVO<T>();
        pv.setList(page.getRecords());
        pv.setTotal(page.getTotal());
        pv.setSize(page.getSize());
        pv.setPages(page.getPages());
        return pv;
    }

    /**
     * 包装返回成功的处理
     */
    protected <T> ApiResponse<T> success(T t) {
        return ApiResponse.ofSuccess(t);
    }

    /**
     * 成功消息
     */
    protected ApiResponse<?> successMessage(String msg) {
        return ApiResponse.ofMessage(msg);
    }

    /**
     * 添加成功
     */
    protected ApiResponse<?> successAdd() {
        return ApiResponse.ofMessage("添加成功");
    }

    /**
     * 更新成功
     */
    protected ApiResponse<?> successUpdate() {
        return ApiResponse.ofMessage("更新成功");
    }

    /**
     * 删除成功
     */
    protected ApiResponse<?> successDelete() {
        return ApiResponse.ofMessage("删除成功");
    }

    /**
     * 包装分页返回成功的处理
     */
    protected <T> ApiResponse<PageVO<T>> successPage(IPage<T> p) {
        return ApiResponse.ofSuccess(responsePage(p));
    }

    /**
     * request 转 实体
     */
    protected <T> T request2Entity(Object request, Class<T> tClass) {
        return BeanUtil.copyProperties(request, tClass);
    }

    /**
     * 通过id和parentId构建树结构
     * @param objList 需要构建的实体类列表
     * @return 返回树行结构
     */
    protected List<Map<String, Object>> treeIdAndParentId(List<?> objList) {
        return treeIdAndParentId(0L, objList.stream().map(BeanUtil::beanToMap).collect(Collectors.toList()), "id", "parentId", "children");
    }

    /**
     * 构建树实体树结构
     * @param parentId 父ID
     * @param list 构建树的列表
     * @param idKey 构建树关系的子ID key
     * @param parentIdKey 构建树关系的父ID key
     * @param childrenKey 用来存放子级列表的key
     * @return 返回树行结构
     */
    protected List<Map<String, Object>> treeIdAndParentId(Object parentId, List<Map<String, Object>> list, String idKey, String parentIdKey, String childrenKey) {
        val childArr = list.stream().filter(p -> (parentId != null && parentId.equals(p.get(parentIdKey))) || p.get(parentIdKey) == null).collect(Collectors.toList());
        list.removeAll(childArr);
        for (Map<String, Object> child : childArr) {
            child.put(childrenKey, treeIdAndParentId(child.get(idKey), list, idKey, parentIdKey, childrenKey));
        }
        return childArr;
    }
}
