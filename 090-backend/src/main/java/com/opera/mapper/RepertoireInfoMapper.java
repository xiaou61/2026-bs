package com.opera.mapper;

import com.opera.entity.RepertoireInfo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface RepertoireInfoMapper {
    List<RepertoireInfo> selectList(@Param("courseName") String courseName, @Param("artistId") Long artistId, @Param("termId") Long termId, @Param("status") Integer status);
    RepertoireInfo selectById(@Param("id") Long id);
    List<RepertoireInfo> selectEnabled();
    long countAll();
    int insert(RepertoireInfo entity);
    int update(RepertoireInfo entity);
    int deleteById(@Param("id") Long id);
}


