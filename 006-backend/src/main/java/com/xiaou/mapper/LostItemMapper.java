package com.xiaou.mapper;

import com.xiaou.entity.LostItem;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import java.util.List;
import java.util.Map;

@Mapper
public interface LostItemMapper {
    List<LostItem> findAll(@Param("params") Map<String, Object> params);
    
    LostItem findById(@Param("id") Long id);
    
    List<LostItem> findByUserId(@Param("userId") Long userId);
    
    int insert(LostItem lostItem);
    
    int update(LostItem lostItem);
    
    int deleteById(@Param("id") Long id);
    
    int updateStatus(@Param("id") Long id, @Param("status") Integer status);
    
    int incrementViews(@Param("id") Long id);
    
    int countAll(@Param("params") Map<String, Object> params);
}

