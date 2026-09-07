package com.teachres.mapper;

import com.teachres.entity.MaterialCategory;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface MaterialCategoryMapper {
    MaterialCategory selectById(Long id);
    List<MaterialCategory> selectList(@Param("name") String name, @Param("status") Integer status);
    List<MaterialCategory> selectEnabledList();
    int insert(MaterialCategory category);
    int update(MaterialCategory category);
    int deleteById(Long id);
}
