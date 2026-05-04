package com.recruitmatch.service;

import com.recruitmatch.entity.JobRequirement;
import com.recruitmatch.mapper.JobRequirementMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

@Service
public class JobRequirementService extends ServiceImpl<JobRequirementMapper, JobRequirement> {
}
