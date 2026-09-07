package com.programming.learning.common;

import lombok.Data;

/**
 * 分页查询请求
 */
@Data
public class PageQuery {
    /**
     * 当前页码
     */
    private Integer pageNum = 1;

    /**
     * 每页大小
     */
    private Integer pageSize = 10;

    /**
     * 排序字段
     */
    private String orderBy;

    /**
     * 排序方式（ASC/DESC）
     */
    private String sortOrder = "DESC";

    /**
     * 获取偏移量
     */
    public Integer getOffset() {
        return (pageNum - 1) * pageSize;
    }

    /**
     * 获取限制数量
     */
    public Integer getLimit() {
        return pageSize;
    }
}
