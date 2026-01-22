package com.xiaou.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.xiaou.entity.MakeupRequest;

public interface MakeupRequestService extends IService<MakeupRequest> {
    MakeupRequest submitMakeup(MakeupRequest request);
    void approveMakeup(Long requestId, Long teacherId, Integer status, String rejectReason);
    IPage<MakeupRequest> pageStudentMakeups(Integer page, Integer size, Long studentId);
    IPage<MakeupRequest> pageTeacherMakeups(Integer page, Integer size, Long teacherId, Integer status);
}
