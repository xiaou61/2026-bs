package com.kindergarten.mapper;

import com.kindergarten.entity.CampusInfo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface CampusInfoMapper {
    List<CampusInfo> selectList(@Param("name") String name, @Param("status") Integer status);
    List<CampusInfo> selectEnabled();
    CampusInfo selectById(@Param("id") Long id);
    int insert(CampusInfo entity);
    int update(CampusInfo entity);
    int deleteById(@Param("id") Long id);
}
