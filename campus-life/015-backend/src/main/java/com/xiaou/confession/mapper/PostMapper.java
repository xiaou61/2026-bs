package com.xiaou.confession.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xiaou.confession.entity.Post;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface PostMapper extends BaseMapper<Post> {
}

