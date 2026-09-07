package com.online.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.online.entity.Comment;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface CommentMapper extends BaseMapper<Comment> {
}
