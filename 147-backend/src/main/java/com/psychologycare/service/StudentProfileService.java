package com.psychologycare.service;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.psychologycare.entity.StudentProfile;
import com.psychologycare.mapper.StudentProfileMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class StudentProfileService extends BaseCrudService<StudentProfile> {
    private final StudentProfileMapper labRoomMapper;

    @Override
    protected BaseMapper<StudentProfile> mapper() {
        return labRoomMapper;
    }

    @Override
    protected String[] keywordColumns() {
        return new String[]{"lab_no", "lab_name", "building_name", "manager_name"};
    }
}







