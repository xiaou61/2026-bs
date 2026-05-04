package com.recruitmatch.service;

import com.recruitmatch.entity.JobPosition;
import com.recruitmatch.mapper.JobPositionMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

@Service
public class JobPositionService extends ServiceImpl<JobPositionMapper, JobPosition> {
}
