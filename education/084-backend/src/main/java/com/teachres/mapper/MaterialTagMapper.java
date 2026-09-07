package com.teachres.mapper;

import com.teachres.entity.MaterialTag;

import java.util.List;

public interface MaterialTagMapper {
    List<MaterialTag> selectEnabledList();
}
