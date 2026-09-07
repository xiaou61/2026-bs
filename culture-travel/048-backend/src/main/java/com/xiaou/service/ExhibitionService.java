package com.xiaou.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xiaou.entity.Exhibition;

public interface ExhibitionService {
    Page<Exhibition> page(int current, int size, String keyword, Integer status);
    Exhibition getById(Long id);
    void save(Exhibition exhibition);
    void update(Exhibition exhibition);
    void delete(Long id);
}
