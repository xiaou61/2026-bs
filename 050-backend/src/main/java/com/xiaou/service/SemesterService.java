package com.xiaou.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xiaou.entity.Semester;

public interface SemesterService extends IService<Semester> {
    Semester getCurrentSemester();
    void setCurrentSemester(Long semesterId);
}
