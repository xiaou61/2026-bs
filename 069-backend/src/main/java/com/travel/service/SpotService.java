package com.teacher.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.teacher.common.BusinessException;
import com.teacher.common.PageResult;
import com.teacher.entity.ScenicSpot;
import com.teacher.mapper.ScenicSpotMapper;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class SpotService {

    @Resource
    private ScenicSpotMapper scenicSpotMapper;

    public PageResult<ScenicSpot> page(Integer pageNum, Integer pageSize, String name, String city, Integer status) {
        Page<ScenicSpot> page = new Page<>(pageNum, pageSize);
        LambdaQueryWrapper<ScenicSpot> wrapper = new LambdaQueryWrapper<ScenicSpot>()
                .like(StringUtils.hasText(name), ScenicSpot::getName, name == null ? null : name.trim())
                .like(StringUtils.hasText(city), ScenicSpot::getCity, city == null ? null : city.trim())
                .eq(status != null, ScenicSpot::getStatus, status)
                .orderByDesc(ScenicSpot::getId);
        Page<ScenicSpot> resultPage = scenicSpotMapper.selectPage(page, wrapper);
        PageResult<ScenicSpot> result = new PageResult<>();
        result.setTotal(resultPage.getTotal());
        result.setRecords(resultPage.getRecords());
        return result;
    }

    public List<ScenicSpot> list(String name, String city) {
        LambdaQueryWrapper<ScenicSpot> wrapper = new LambdaQueryWrapper<ScenicSpot>()
                .eq(ScenicSpot::getStatus, 1)
                .like(StringUtils.hasText(name), ScenicSpot::getName, name == null ? null : name.trim())
                .like(StringUtils.hasText(city), ScenicSpot::getCity, city == null ? null : city.trim())
                .orderByDesc(ScenicSpot::getId);
        return scenicSpotMapper.selectList(wrapper);
    }

    public void save(ScenicSpot scenicSpot) {
        if (scenicSpot == null || !StringUtils.hasText(scenicSpot.getName()) || !StringUtils.hasText(scenicSpot.getCity()) || scenicSpot.getPrice() == null) {
            throw new BusinessException("景点参数不完整");
        }
        if (scenicSpot.getPrice().compareTo(BigDecimal.ZERO) < 0) {
            throw new BusinessException("价格不能小于0");
        }
        if (scenicSpot.getId() == null) {
            add(scenicSpot);
        } else {
            update(scenicSpot);
        }
    }

    private void add(ScenicSpot scenicSpot) {
        scenicSpot.setName(scenicSpot.getName().trim());
        scenicSpot.setCity(scenicSpot.getCity().trim());
        scenicSpot.setTags(StringUtils.hasText(scenicSpot.getTags()) ? scenicSpot.getTags().trim() : "");
        scenicSpot.setCover(StringUtils.hasText(scenicSpot.getCover()) ? scenicSpot.getCover().trim() : "");
        scenicSpot.setIntro(StringUtils.hasText(scenicSpot.getIntro()) ? scenicSpot.getIntro().trim() : "");
        scenicSpot.setStatus(scenicSpot.getStatus() == null ? 1 : (scenicSpot.getStatus() == 0 ? 0 : 1));
        scenicSpotMapper.insert(scenicSpot);
    }

    private void update(ScenicSpot scenicSpot) {
        ScenicSpot db = scenicSpotMapper.selectById(scenicSpot.getId());
        if (db == null) {
            throw new BusinessException("景点不存在");
        }
        db.setName(scenicSpot.getName().trim());
        db.setCity(scenicSpot.getCity().trim());
        db.setTags(StringUtils.hasText(scenicSpot.getTags()) ? scenicSpot.getTags().trim() : "");
        db.setPrice(scenicSpot.getPrice());
        db.setCover(StringUtils.hasText(scenicSpot.getCover()) ? scenicSpot.getCover().trim() : "");
        db.setIntro(StringUtils.hasText(scenicSpot.getIntro()) ? scenicSpot.getIntro().trim() : "");
        if (scenicSpot.getStatus() != null) {
            db.setStatus(scenicSpot.getStatus() == 0 ? 0 : 1);
        }
        scenicSpotMapper.updateById(db);
    }

    public void updateStatus(Long id, Integer status) {
        if (status == null || (status != 0 && status != 1)) {
            throw new BusinessException("状态不合法");
        }
        ScenicSpot db = scenicSpotMapper.selectById(id);
        if (db == null) {
            throw new BusinessException("景点不存在");
        }
        db.setStatus(status);
        scenicSpotMapper.updateById(db);
    }

    public void deleteById(Long id) {
        scenicSpotMapper.deleteById(id);
    }

    public ScenicSpot getEnabledById(Long id) {
        if (id == null) {
            return null;
        }
        return scenicSpotMapper.selectOne(new LambdaQueryWrapper<ScenicSpot>()
                .eq(ScenicSpot::getId, id)
                .eq(ScenicSpot::getStatus, 1)
                .last("limit 1"));
    }

    public Map<Long, ScenicSpot> mapByIds(Collection<Long> ids) {
        Map<Long, ScenicSpot> map = new HashMap<>();
        if (ids == null || ids.isEmpty()) {
            return map;
        }
        Set<Long> idSet = ids.stream().filter(java.util.Objects::nonNull).collect(Collectors.toSet());
        if (idSet.isEmpty()) {
            return map;
        }
        List<ScenicSpot> list = scenicSpotMapper.selectBatchIds(idSet);
        for (ScenicSpot item : list) {
            map.put(item.getId(), item);
        }
        return map;
    }

    public Long countAll() {
        return scenicSpotMapper.selectCount(null);
    }
}

