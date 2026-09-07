package com.xiaou.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.xiaou.dto.ScriptDTO;
import com.xiaou.entity.Script;

public interface ScriptService extends IService<Script> {
    void createScript(Long authorId, ScriptDTO dto);
    void updateScript(Long authorId, ScriptDTO dto);
    Page<Script> pageScripts(int current, int size, Long categoryId, Integer type, Integer difficulty, String keyword);
}
