package com.droneinspect.service;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.droneinspect.entity.DefectImage;
import com.droneinspect.mapper.DefectImageMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DefectImageService extends BaseCrudService<DefectImage> {
    private final DefectImageMapper mapper;

    @Override
    protected BaseMapper<DefectImage> mapper() {
        return mapper;
    }

    @Override
    protected String[] keywordColumns() {
        return new String[]{"image_no", "defect_no", "file_name", "ai_label"};
    }
}
