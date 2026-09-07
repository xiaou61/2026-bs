package com.teachres.mapper;

import com.teachres.entity.MaterialFavorite;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface MaterialFavoriteMapper {
    MaterialFavorite selectByUserAndMaterial(@Param("userId") Long userId, @Param("materialId") Long materialId);
    List<MaterialFavorite> selectByUserId(Long userId);
    int insert(MaterialFavorite favorite);
    int deleteByUserAndMaterial(@Param("userId") Long userId, @Param("materialId") Long materialId);
}
