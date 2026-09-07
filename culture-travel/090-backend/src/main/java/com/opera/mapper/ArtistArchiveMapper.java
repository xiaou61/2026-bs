package com.opera.mapper;

import com.opera.entity.ArtistArchive;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ArtistArchiveMapper {
    List<ArtistArchive> selectList(@Param("name") String name, @Param("departmentId") Long departmentId, @Param("status") Integer status);
    List<ArtistArchive> selectEnabled();
    ArtistArchive selectById(@Param("id") Long id);
    int insert(ArtistArchive entity);
    int update(ArtistArchive entity);
    int deleteById(@Param("id") Long id);
}


