package com.learningpath.service;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.learningpath.entity.CourseCatalog;
import com.learningpath.mapper.CourseCatalogMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CourseCatalogService extends BaseCrudService<CourseCatalog> {
    private final CourseCatalogMapper courseCatalogMapper;

    @Override
    protected BaseMapper<CourseCatalog> mapper() {
        return courseCatalogMapper;
    }

    @Override
    protected String[] keywordColumns() {
        return new String[]{"course_no", "course_name", "lecturer_name", "delivery_mode"};
    }
}



