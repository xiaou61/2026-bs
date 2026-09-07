package com.teachres.mapper;

import com.teachres.entity.SystemNotice;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SystemNoticeMapper {
    SystemNotice selectById(@Param("id") Long id);

    List<SystemNotice> selectList(@Param("title") String title,
                                  @Param("status") Integer status);

    List<SystemNotice> selectPublicList();

    int insert(SystemNotice notice);

    int update(SystemNotice notice);

    int deleteById(@Param("id") Long id);

    int incrementViewCount(@Param("id") Long id);
}
