package com.xiaou.ailearning.common;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 分页结果
 */
@Data
@AllArgsConstructor
public class PageResult<T> {
    
    private Long total;
    private List<T> records;
    private Long current;
    private Long size;
    
    public static <T> PageResult<T> of(Long total, List<T> records, Long current, Long size) {
        return new PageResult<>(total, records, current, size);
    }

    public List<T> getList() {
        return records;
    }

    public Map<String, Object> getPagination() {
        long currentPage = current != null ? current : 1L;
        long pageSize = size != null && size > 0 ? size : 10L;
        long totalCount = total != null ? total : 0L;
        long totalPages = totalCount == 0 ? 0L : (totalCount + pageSize - 1) / pageSize;

        Map<String, Object> pagination = new HashMap<>();
        pagination.put("total", totalCount);
        pagination.put("currentPage", currentPage);
        pagination.put("pageSize", pageSize);
        pagination.put("totalPages", totalPages);
        return pagination;
    }
}
