package com.xiaou.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xiaou.common.BusinessException;
import com.xiaou.entity.Bed;
import com.xiaou.entity.Elder;
import com.xiaou.mapper.BedMapper;
import com.xiaou.mapper.ElderMapper;
import com.xiaou.service.ElderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class ElderServiceImpl extends ServiceImpl<ElderMapper, Elder> implements ElderService {

    private final BedMapper bedMapper;

    @Override
    public IPage<Elder> pageList(Integer current, Integer size, String name, Integer status, Integer careLevel) {
        LambdaQueryWrapper<Elder> wrapper = new LambdaQueryWrapper<>();
        if (StringUtils.hasText(name)) {
            wrapper.like(Elder::getName, name);
        }
        if (status != null) {
            wrapper.eq(Elder::getStatus, status);
        }
        if (careLevel != null) {
            wrapper.eq(Elder::getCareLevel, careLevel);
        }
        wrapper.orderByDesc(Elder::getCreateTime);
        return page(new Page<>(current, size), wrapper);
    }

    @Override
    @Transactional
    public void checkIn(Elder elder) {
        if (elder.getBedId() != null) {
            Bed bed = bedMapper.selectById(elder.getBedId());
            if (bed == null || bed.getStatus() != 0) {
                throw new BusinessException("床位不可用");
            }
            bed.setStatus(1);
            bedMapper.updateById(bed);
        }
        elder.setStatus(1);
        elder.setCheckInDate(LocalDate.now());
        save(elder);
    }

    @Override
    @Transactional
    public void checkOut(Long id) {
        Elder elder = getById(id);
        if (elder == null) {
            throw new BusinessException("老人不存在");
        }
        if (elder.getStatus() == 0) {
            throw new BusinessException("老人已退住");
        }
        if (elder.getBedId() != null) {
            Bed bed = bedMapper.selectById(elder.getBedId());
            if (bed != null) {
                bed.setStatus(0);
                bedMapper.updateById(bed);
            }
        }
        elder.setStatus(0);
        elder.setCheckOutDate(LocalDate.now());
        elder.setBedId(null);
        updateById(elder);
    }
}
