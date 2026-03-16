package com.opera.mapper;

import com.opera.entity.OperaCategory;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface OperaCategoryMapper {
    List<OperaCategory> selectList(@Param("name") String name, @Param("status") Integer status);
    List<OperaCategory> selectEnabled();
    OperaCategory selectById(@Param("id") Long id);
    int insert(OperaCategory entity);
    int update(OperaCategory entity);
    int deleteById(@Param("id") Long id);
}


