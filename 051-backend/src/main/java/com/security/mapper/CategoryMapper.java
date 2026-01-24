package com.security.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.security.entity.KnowledgeCategory;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface CategoryMapper extends BaseMapper<KnowledgeCategory> {
}
