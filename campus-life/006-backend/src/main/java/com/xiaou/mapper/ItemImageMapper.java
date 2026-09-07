package com.xiaou.mapper;

import com.xiaou.entity.ItemImage;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import java.util.List;

@Mapper
public interface ItemImageMapper {
    List<ItemImage> findByItem(@Param("itemType") String itemType, @Param("itemId") Long itemId);
    
    int insert(ItemImage itemImage);
    
    int deleteById(@Param("id") Long id);
    
    int deleteByItem(@Param("itemType") String itemType, @Param("itemId") Long itemId);
}

