package com.xiaou.herbal.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xiaou.herbal.entity.Comment;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface CommentMapper extends BaseMapper<Comment> {
}
