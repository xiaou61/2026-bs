package com.teacher.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.teacher.common.BusinessException;
import com.teacher.common.PageResult;
import com.teacher.entity.Traveler;
import com.teacher.mapper.TravelerMapper;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.List;

@Service
public class TravelerService {

    @Resource
    private TravelerMapper travelerMapper;

    public PageResult<Traveler> page(Long userId, Integer pageNum, Integer pageSize, String name) {
        Page<Traveler> page = new Page<>(pageNum, pageSize);
        LambdaQueryWrapper<Traveler> wrapper = new LambdaQueryWrapper<Traveler>()
                .eq(Traveler::getUserId, userId)
                .like(StringUtils.hasText(name), Traveler::getName, name == null ? null : name.trim())
                .orderByDesc(Traveler::getIsDefault)
                .orderByDesc(Traveler::getId);
        Page<Traveler> resultPage = travelerMapper.selectPage(page, wrapper);
        PageResult<Traveler> result = new PageResult<>();
        result.setTotal(resultPage.getTotal());
        result.setRecords(resultPage.getRecords());
        return result;
    }

    public List<Traveler> list(Long userId) {
        return travelerMapper.selectList(new LambdaQueryWrapper<Traveler>()
                .eq(Traveler::getUserId, userId)
                .orderByDesc(Traveler::getIsDefault)
                .orderByDesc(Traveler::getId));
    }

    public void save(Long userId, Traveler traveler) {
        if (traveler == null || !StringUtils.hasText(traveler.getName()) || !StringUtils.hasText(traveler.getCertNo()) || !StringUtils.hasText(traveler.getPhone())) {
            throw new BusinessException("出行人信息不完整");
        }
        if (traveler.getId() == null) {
            add(userId, traveler);
        } else {
            update(userId, traveler);
        }
    }

    private void add(Long userId, Traveler traveler) {
        traveler.setUserId(userId);
        traveler.setName(traveler.getName().trim());
        traveler.setCertType(StringUtils.hasText(traveler.getCertType()) ? traveler.getCertType().trim() : "身份证");
        traveler.setCertNo(traveler.getCertNo().trim());
        traveler.setPhone(traveler.getPhone().trim());
        traveler.setIsDefault(traveler.getIsDefault() != null && traveler.getIsDefault() == 1 ? 1 : 0);
        if (traveler.getIsDefault() == 1) {
            clearDefault(userId);
        }
        travelerMapper.insert(traveler);
    }

    private void update(Long userId, Traveler traveler) {
        Traveler db = travelerMapper.selectById(traveler.getId());
        if (db == null || !db.getUserId().equals(userId)) {
            throw new BusinessException("出行人不存在");
        }
        db.setName(traveler.getName().trim());
        db.setCertType(StringUtils.hasText(traveler.getCertType()) ? traveler.getCertType().trim() : "身份证");
        db.setCertNo(traveler.getCertNo().trim());
        db.setPhone(traveler.getPhone().trim());
        db.setIsDefault(traveler.getIsDefault() != null && traveler.getIsDefault() == 1 ? 1 : 0);
        if (db.getIsDefault() == 1) {
            clearDefault(userId);
        }
        travelerMapper.updateById(db);
    }

    public void deleteById(Long userId, Long id) {
        Traveler db = travelerMapper.selectById(id);
        if (db == null || !db.getUserId().equals(userId)) {
            throw new BusinessException("出行人不存在");
        }
        travelerMapper.deleteById(id);
    }

    public Long countByUser(Long userId) {
        return travelerMapper.selectCount(new LambdaQueryWrapper<Traveler>().eq(Traveler::getUserId, userId));
    }

    private void clearDefault(Long userId) {
        travelerMapper.update(null, new LambdaUpdateWrapper<Traveler>()
                .set(Traveler::getIsDefault, 0)
                .eq(Traveler::getUserId, userId)
                .eq(Traveler::getIsDefault, 1));
    }
}

