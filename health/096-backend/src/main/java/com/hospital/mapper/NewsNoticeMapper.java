package com.hospital.mapper;

import com.hospital.entity.NewsNotice;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface NewsNoticeMapper {
    List<NewsNotice> selectPage(@Param("keyword") String keyword, @Param("status") Integer status);

    List<NewsNotice> selectPublicList();

    void insert(NewsNotice entity);

    void update(NewsNotice entity);

    void deleteById(@Param("id") Long id);
}
