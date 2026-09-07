package com.xiaou.confession.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xiaou.confession.entity.Comment;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface CommentMapper extends BaseMapper<Comment> {
}

