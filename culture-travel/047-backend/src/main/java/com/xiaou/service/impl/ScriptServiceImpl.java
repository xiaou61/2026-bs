package com.xiaou.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xiaou.dto.ScriptContentDTO;
import com.xiaou.dto.ScriptDTO;
import com.xiaou.entity.Script;
import com.xiaou.entity.ScriptContent;
import com.xiaou.mapper.ScriptContentMapper;
import com.xiaou.mapper.ScriptMapper;
import com.xiaou.service.ScriptService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

@Service
@RequiredArgsConstructor
public class ScriptServiceImpl extends ServiceImpl<ScriptMapper, Script> implements ScriptService {

    private final ScriptContentMapper contentMapper;

    @Override
    @Transactional
    public void createScript(Long authorId, ScriptDTO dto) {
        Script script = new Script();
        script.setAuthorId(authorId);
        script.setCategoryId(dto.getCategoryId());
        script.setTitle(dto.getTitle());
        script.setCover(dto.getCover());
        script.setDescription(dto.getDescription());
        script.setDifficulty(dto.getDifficulty());
        script.setPlayerCount(dto.getPlayerCount());
        script.setDuration(dto.getDuration());
        script.setType(dto.getType());
        script.setPrice(dto.getPrice());
        script.setStatus(0);
        script.setViewCount(0);
        script.setLikeCount(0);
        save(script);

        if (dto.getContents() != null) {
            for (ScriptContentDTO c : dto.getContents()) {
                ScriptContent content = new ScriptContent();
                content.setScriptId(script.getId());
                content.setChapterName(c.getChapterName());
                content.setRoleName(c.getRoleName());
                content.setContent(c.getContent());
                content.setSort(c.getSort());
                contentMapper.insert(content);
            }
        }
    }

    @Override
    @Transactional
    public void updateScript(Long authorId, ScriptDTO dto) {
        Script script = getById(dto.getId());
        if (script == null || !script.getAuthorId().equals(authorId)) {
            throw new RuntimeException("无权操作");
        }
        script.setCategoryId(dto.getCategoryId());
        script.setTitle(dto.getTitle());
        script.setCover(dto.getCover());
        script.setDescription(dto.getDescription());
        script.setDifficulty(dto.getDifficulty());
        script.setPlayerCount(dto.getPlayerCount());
        script.setDuration(dto.getDuration());
        script.setType(dto.getType());
        script.setPrice(dto.getPrice());
        updateById(script);
    }

    @Override
    public Page<Script> pageScripts(int current, int size, Long categoryId, Integer type, Integer difficulty, String keyword) {
        LambdaQueryWrapper<Script> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Script::getStatus, 1);
        if (categoryId != null) wrapper.eq(Script::getCategoryId, categoryId);
        if (type != null) wrapper.eq(Script::getType, type);
        if (difficulty != null) wrapper.eq(Script::getDifficulty, difficulty);
        if (StringUtils.hasText(keyword)) wrapper.like(Script::getTitle, keyword);
        wrapper.orderByDesc(Script::getViewCount);
        return page(new Page<>(current, size), wrapper);
    }
}
