package com.teachres.mapper;

import com.teachres.entity.MaterialFile;

public interface MaterialFileMapper {
    MaterialFile selectByMaterialId(Long materialId);
    int insert(MaterialFile materialFile);
    int update(MaterialFile materialFile);
    int deleteByMaterialId(Long materialId);
}
