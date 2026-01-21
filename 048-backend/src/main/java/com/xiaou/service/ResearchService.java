package com.xiaou.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xiaou.dto.ResearchDTO;
import com.xiaou.entity.Research;

public interface ResearchService {
    Page<Research> page(int current, int size, String keyword, Integer status);
    Page<Research> pageByAuthor(Long authorId, int current, int size);
    Research getById(Long id);
    void save(Long authorId, ResearchDTO dto);
    void update(ResearchDTO dto);
    void audit(Long id, Integer status);
}
