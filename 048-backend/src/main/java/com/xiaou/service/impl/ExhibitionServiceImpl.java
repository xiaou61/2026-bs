package com.xiaou.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xiaou.entity.Exhibition;
import com.xiaou.entity.ExhibitionHall;
import com.xiaou.mapper.ExhibitionHallMapper;
import com.xiaou.mapper.ExhibitionMapper;
import com.xiaou.service.ExhibitionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
@RequiredArgsConstructor
public class ExhibitionServiceImpl implements ExhibitionService {
    private final ExhibitionMapper exhibitionMapper;
    private final ExhibitionHallMapper hallMapper;

    @Override
    public Page<Exhibition> page(int current, int size, String keyword, Integer status) {
        LambdaQueryWrapper<Exhibition> wrapper = new LambdaQueryWrapper<>();
        if (StringUtils.hasText(keyword)) {
            wrapper.like(Exhibition::getTitle, keyword);
        }
        if (status != null) wrapper.eq(Exhibition::getStatus, status);
        wrapper.orderByDesc(Exhibition::getCreateTime);
        Page<Exhibition> page = exhibitionMapper.selectPage(new Page<>(current, size), wrapper);
        page.getRecords().forEach(e -> {
            if (e.getHallId() != null) {
                ExhibitionHall h = hallMapper.selectById(e.getHallId());
                if (h != null) e.setHallName(h.getName());
            }
        });
        return page;
    }

    @Override
    public Exhibition getById(Long id) {
        Exhibition e = exhibitionMapper.selectById(id);
        if (e != null && e.getHallId() != null) {
            ExhibitionHall h = hallMapper.selectById(e.getHallId());
            if (h != null) e.setHallName(h.getName());
        }
        return e;
    }

    @Override
    public void save(Exhibition exhibition) {
        exhibition.setViewCount(0);
        exhibitionMapper.insert(exhibition);
    }

    @Override
    public void update(Exhibition exhibition) {
        exhibitionMapper.updateById(exhibition);
    }

    @Override
    public void delete(Long id) {
        exhibitionMapper.deleteById(id);
    }
}
