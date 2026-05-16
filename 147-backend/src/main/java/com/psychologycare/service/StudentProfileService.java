package com.psychologycare.service;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.psychologycare.entity.StudentProfile;
import com.psychologycare.mapper.StudentProfileMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class StudentProfileService extends BaseCrudService<StudentProfile> {
    private final StudentProfileMapper studentProfileMapper;

    @Override
    protected BaseMapper<StudentProfile> mapper() {
        return studentProfileMapper;
    }

    @Override
    protected String[] keywordColumns() {
        return new String[]{"student_no", "student_name", "class_name", "focus_tag"};
    }
}







