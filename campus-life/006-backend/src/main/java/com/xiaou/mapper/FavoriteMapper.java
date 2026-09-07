package com.xiaou.mapper;

import com.xiaou.entity.Favorite;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import java.util.List;

@Mapper
public interface FavoriteMapper {
    List<Favorite> findByUserId(@Param("userId") Long userId);
    
    Favorite findOne(@Param("userId") Long userId, @Param("itemType") String itemType, @Param("itemId") Long itemId);
    
    int insert(Favorite favorite);
    
    int deleteById(@Param("id") Long id);
    
    int deleteByUserAndItem(@Param("userId") Long userId, @Param("itemType") String itemType, @Param("itemId") Long itemId);
}

