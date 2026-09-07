package com.xiaou.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xiaou.dto.RelicDTO;
import com.xiaou.entity.*;
import com.xiaou.mapper.*;
import com.xiaou.service.RelicService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RelicServiceImpl implements RelicService {
    private final RelicMapper relicMapper;
    private final RelicImageMapper relicImageMapper;
    private final RelicCategoryMapper categoryMapper;
    private final DynastyMapper dynastyMapper;
    private final ExhibitionHallMapper hallMapper;

    @Override
    public Page<Relic> page(int current, int size, String keyword, Long categoryId, Long dynastyId, Integer level) {
        LambdaQueryWrapper<Relic> wrapper = new LambdaQueryWrapper<>();
        if (StringUtils.hasText(keyword)) {
            wrapper.like(Relic::getName, keyword).or().like(Relic::getRelicNo, keyword);
        }
        if (categoryId != null) wrapper.eq(Relic::getCategoryId, categoryId);
        if (dynastyId != null) wrapper.eq(Relic::getDynastyId, dynastyId);
        if (level != null) wrapper.eq(Relic::getLevel, level);
        wrapper.orderByDesc(Relic::getCreateTime);
        Page<Relic> page = relicMapper.selectPage(new Page<>(current, size), wrapper);
        page.getRecords().forEach(this::fillRelicInfo);
        return page;
    }

    @Override
    public Relic getById(Long id) {
        Relic relic = relicMapper.selectById(id);
        if (relic != null) fillRelicInfo(relic);
        return relic;
    }

    @Override
    public List<RelicImage> getImages(Long relicId) {
        return relicImageMapper.selectList(new LambdaQueryWrapper<RelicImage>()
                .eq(RelicImage::getRelicId, relicId).orderByAsc(RelicImage::getSort));
    }

    @Override
    public void save(RelicDTO dto) {
        Relic relic = new Relic();
        BeanUtils.copyProperties(dto, relic);
        relic.setViewCount(0);
        relic.setLikeCount(0);
        relicMapper.insert(relic);
    }

    @Override
    public void update(RelicDTO dto) {
        Relic relic = new Relic();
        BeanUtils.copyProperties(dto, relic);
        relicMapper.updateById(relic);
    }

    @Override
    public void updateStatus(Long id, Integer status) {
        Relic relic = new Relic();
        relic.setId(id);
        relic.setStatus(status);
        relicMapper.updateById(relic);
    }

    @Override
    public void addViewCount(Long id) {
        Relic relic = relicMapper.selectById(id);
        if (relic != null) {
            relic.setViewCount(relic.getViewCount() + 1);
            relicMapper.updateById(relic);
        }
    }

    @Override
    public void addLikeCount(Long id) {
        Relic relic = relicMapper.selectById(id);
        if (relic != null) {
            relic.setLikeCount(relic.getLikeCount() + 1);
            relicMapper.updateById(relic);
        }
    }

    private void fillRelicInfo(Relic relic) {
        if (relic.getCategoryId() != null) {
            RelicCategory c = categoryMapper.selectById(relic.getCategoryId());
            if (c != null) relic.setCategoryName(c.getName());
        }
        if (relic.getDynastyId() != null) {
            Dynasty d = dynastyMapper.selectById(relic.getDynastyId());
            if (d != null) relic.setDynastyName(d.getName());
        }
        if (relic.getHallId() != null) {
            ExhibitionHall h = hallMapper.selectById(relic.getHallId());
            if (h != null) relic.setHallName(h.getName());
        }
    }
}
