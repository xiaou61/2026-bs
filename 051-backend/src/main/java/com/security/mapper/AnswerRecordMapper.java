package com.security.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.security.entity.AnswerRecord;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

@Mapper
public interface AnswerRecordMapper extends BaseMapper<AnswerRecord> {
    @Select("SELECT u.id, u.nickname, u.avatar, u.points FROM user u ORDER BY u.points DESC LIMIT #{limit}")
    List<Map<String, Object>> getRankList(Integer limit);
}
