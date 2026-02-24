package com.harbin.tourism.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.harbin.tourism.entity.Favorite;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface FavoriteMapper extends BaseMapper<Favorite> {
}
