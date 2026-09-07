package com.xiaou.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xiaou.dto.ResearchDTO;
import com.xiaou.entity.Relic;
import com.xiaou.entity.Research;
import com.xiaou.entity.User;
import com.xiaou.mapper.RelicMapper;
import com.xiaou.mapper.ResearchMapper;
import com.xiaou.mapper.UserMapper;
import com.xiaou.service.ResearchService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class ResearchServiceImpl implements ResearchService {
    private final ResearchMapper researchMapper;
    private final RelicMapper relicMapper;
    private final UserMapper userMapper;

    @Override
    public Page<Research> page(int current, int size, String keyword, Integer status) {
        LambdaQueryWrapper<Research> wrapper = new LambdaQueryWrapper<>();
        if (StringUtils.hasText(keyword)) wrapper.like(Research::getTitle, keyword);
        if (status != null) wrapper.eq(Research::getStatus, status);
        wrapper.orderByDesc(Research::getCreateTime);
        Page<Research> page = researchMapper.selectPage(new Page<>(current, size), wrapper);
        page.getRecords().forEach(this::fillInfo);
        return page;
    }

    @Override
    public Page<Research> pageByAuthor(Long authorId, int current, int size) {
        LambdaQueryWrapper<Research> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Research::getAuthorId, authorId).orderByDesc(Research::getCreateTime);
        Page<Research> page = researchMapper.selectPage(new Page<>(current, size), wrapper);
        page.getRecords().forEach(this::fillInfo);
        return page;
    }

    @Override
    public Research getById(Long id) {
        Research r = researchMapper.selectById(id);
        if (r != null) {
            fillInfo(r);
            r.setViewCount(r.getViewCount() + 1);
            researchMapper.updateById(r);
        }
        return r;
    }

    @Override
    public void save(Long authorId, ResearchDTO dto) {
        Research r = new Research();
        BeanUtils.copyProperties(dto, r);
        r.setAuthorId(authorId);
        r.setStatus(0);
        r.setViewCount(0);
        researchMapper.insert(r);
    }

    @Override
    public void update(ResearchDTO dto) {
        Research r = new Research();
        BeanUtils.copyProperties(dto, r);
        researchMapper.updateById(r);
    }

    @Override
    public void audit(Long id, Integer status) {
        Research r = new Research();
        r.setId(id);
        r.setStatus(status);
        if (status == 1) r.setPublishDate(LocalDate.now());
        researchMapper.updateById(r);
    }

    private void fillInfo(Research r) {
        if (r.getRelicId() != null) {
            Relic relic = relicMapper.selectById(r.getRelicId());
            if (relic != null) r.setRelicName(relic.getName());
        }
        if (r.getAuthorId() != null) {
            User u = userMapper.selectById(r.getAuthorId());
            if (u != null) r.setAuthorName(u.getRealName());
        }
    }
}
