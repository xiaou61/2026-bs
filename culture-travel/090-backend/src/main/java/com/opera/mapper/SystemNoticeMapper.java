package com.opera.mapper;

import com.opera.entity.SystemNotice;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SystemNoticeMapper {
    List<SystemNotice> selectList(@Param("title") String title, @Param("status") Integer status);
    List<SystemNotice> selectPublicList();
    long countAll();
    int insert(SystemNotice entity);
    int update(SystemNotice entity);
    int deleteById(@Param("id") Long id);
}


