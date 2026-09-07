package com.xiaou.mapper;

import com.xiaou.entity.Notice;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface NoticeMapper {
    Notice findById(Long id);
    
    List<Notice> findAll(@Param("keyword") String keyword, @Param("type") String type);
    
    int insert(Notice notice);
    
    int update(Notice notice);
    
    int deleteById(Long id);
}

