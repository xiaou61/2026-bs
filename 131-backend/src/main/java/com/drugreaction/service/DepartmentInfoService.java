package com.drugreaction.service;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.drugreaction.entity.DepartmentInfo;
import com.drugreaction.mapper.DepartmentInfoMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DepartmentInfoService extends BaseCrudService<DepartmentInfo> {
    private final DepartmentInfoMapper departmentInfoMapper;

    @Override
    protected BaseMapper<DepartmentInfo> mapper() {
        return departmentInfoMapper;
    }

    @Override
    protected String[] keywordColumns() {
        return new String[]{"dept_no", "dept_name", "hospital_name", "contact_name"};
    }
}
