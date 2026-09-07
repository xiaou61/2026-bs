package com.xiaou.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xiaou.entity.ShoppingList;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ShoppingListMapper extends BaseMapper<ShoppingList> {
}

