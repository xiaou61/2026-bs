package com.xiaou.wedding.mapper;

import com.xiaou.wedding.entity.Studio;
import org.apache.ibatis.annotations.Param;
import java.util.List;

public interface StudioMapper {
    Studio findById(@Param("id") Long id);

    List<Studio> list();

    int insert(Studio studio);

    int update(Studio studio);

    int logicDelete(@Param("id") Long id);
}
