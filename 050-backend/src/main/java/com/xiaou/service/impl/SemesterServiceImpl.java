package com.xiaou.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xiaou.entity.Semester;
import com.xiaou.mapper.SemesterMapper;
import com.xiaou.service.SemesterService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class SemesterServiceImpl extends ServiceImpl<SemesterMapper, Semester> implements SemesterService {

    @Override
    public Semester getCurrentSemester() {
        return this.getOne(new LambdaQueryWrapper<Semester>()
                .eq(Semester::getIsCurrent, 1));
    }

    @Override
    @Transactional
    public void setCurrentSemester(Long semesterId) {
        // 先将所有学期设为非当前
        this.update(new LambdaUpdateWrapper<Semester>()
                .set(Semester::getIsCurrent, 0));
        // 设置指定学期为当前学期
        this.update(new LambdaUpdateWrapper<Semester>()
                .eq(Semester::getId, semesterId)
                .set(Semester::getIsCurrent, 1));
    }
}
