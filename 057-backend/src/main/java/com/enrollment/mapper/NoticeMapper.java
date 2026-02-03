package com.enrollment.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.enrollment.entity.Notice;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

public interface NoticeMapper extends BaseMapper<Notice> {
    @Select("SELECT n.*, a.name as admin_name FROM notice n " +
            "LEFT JOIN admin a ON n.admin_id = a.id " +
            "WHERE (#{title} IS NULL OR n.title LIKE CONCAT('%', #{title}, '%')) " +
            "AND (#{type} IS NULL OR n.type = #{type})")
    IPage<Notice> selectPageWithAdmin(Page<Notice> page, @Param("title") String title, @Param("type") Integer type);
}
