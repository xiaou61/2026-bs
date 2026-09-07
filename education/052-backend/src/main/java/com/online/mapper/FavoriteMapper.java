package com.online.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.online.entity.Favorite;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface FavoriteMapper extends BaseMapper<Favorite> {
}
