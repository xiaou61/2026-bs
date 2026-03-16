package com.opera.mapper;

import com.opera.entity.VenueInfo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface VenueInfoMapper {
    List<VenueInfo> selectList(@Param("name") String name, @Param("majorId") Long majorId, @Param("status") Integer status);
    List<VenueInfo> selectEnabled();
    VenueInfo selectById(@Param("id") Long id);
    int insert(VenueInfo entity);
    int update(VenueInfo entity);
    int deleteById(@Param("id") Long id);
}


