package com.gongkao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.gongkao.entity.Question;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface QuestionMapper extends BaseMapper<Question> {
}

