package com.blog.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.blog.entity.Post;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Mapper
public interface PostMapper extends BaseMapper<Post> {

    @Select("select date(create_time) as day, count(1) as total from blog_post where create_time between #{start} and #{end} group by date(create_time)")
    List<Map<String, Object>> dailyCreatedCount(@Param("start") LocalDateTime start, @Param("end") LocalDateTime end);
}
