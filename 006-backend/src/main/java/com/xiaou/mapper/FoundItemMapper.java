package com.xiaou.mapper;

import com.xiaou.entity.FoundItem;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import java.util.List;
import java.util.Map;

@Mapper
public interface FoundItemMapper {
    List<FoundItem> findAll(@Param("params") Map<String, Object> params);
    
    FoundItem findById(@Param("id") Long id);
    
    List<FoundItem> findByUserId(@Param("userId") Long userId);
    
    int insert(FoundItem foundItem);
    
    int update(FoundItem foundItem);
    
    int deleteById(@Param("id") Long id);
    
    int updateStatus(@Param("id") Long id, @Param("status") Integer status);
    
    int incrementViews(@Param("id") Long id);
    
    int countAll(@Param("params") Map<String, Object> params);
}

