package com.xiaou.wedding.mapper;

import com.xiaou.wedding.entity.Photographer;
import org.apache.ibatis.annotations.Param;
import java.util.List;

public interface PhotographerMapper {
    Photographer findById(@Param("id") Long id);

    List<Photographer> list(@Param("level") String level);

    int insert(Photographer photographer);

    int update(Photographer photographer);

    int logicDelete(@Param("id") Long id);
}
