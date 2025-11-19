package com.xiaou.ailearning.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xiaou.ailearning.entity.QaRecord;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface QaRecordMapper extends BaseMapper<QaRecord> {
    
    @Select("SELECT * FROM qa_record WHERE user_id = #{userId} ORDER BY create_time DESC")
    List<QaRecord> selectByUserId(Long userId);
    
    @Select("SELECT * FROM qa_record WHERE session_id = #{sessionId} ORDER BY create_time ASC")
    List<QaRecord> selectBySessionId(String sessionId);
    
    @Select("SELECT * FROM qa_record WHERE question_keywords LIKE CONCAT('%', #{keyword}, '%')")
    List<QaRecord> selectByKeyword(String keyword);
}