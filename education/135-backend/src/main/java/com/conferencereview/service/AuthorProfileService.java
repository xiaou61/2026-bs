package com.conferencereview.service;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.conferencereview.entity.AuthorProfile;
import com.conferencereview.mapper.AuthorProfileMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthorProfileService extends BaseCrudService<AuthorProfile> {
    private final AuthorProfileMapper labRoomMapper;

    @Override
    protected BaseMapper<AuthorProfile> mapper() {
        return labRoomMapper;
    }

    @Override
    protected String[] keywordColumns() {
        return new String[]{"lab_no", "lab_name", "building_name", "manager_name"};
    }
}

