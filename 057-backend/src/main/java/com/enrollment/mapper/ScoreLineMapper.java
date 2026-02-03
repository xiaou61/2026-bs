package com.enrollment.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.enrollment.entity.ScoreLine;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

public interface ScoreLineMapper extends BaseMapper<ScoreLine> {
    @Select("SELECT sl.*, m.name as major_name FROM score_line sl " +
            "LEFT JOIN major m ON sl.major_id = m.id " +
            "WHERE (#{year} IS NULL OR sl.year = #{year}) " +
            "AND (#{province} IS NULL OR sl.province = #{province})")
    IPage<ScoreLine> selectPageWithMajor(Page<ScoreLine> page, @Param("year") Integer year, @Param("province") String province);
}
