package com.xiaou.community.mapper;

import com.xiaou.community.entity.Notice;
import org.apache.ibatis.annotations.Mapper;
import java.util.List;

@Mapper
public interface NoticeMapper {
    void insert(Notice notice);
    void update(Notice notice);
    void deleteById(Integer id);
    Notice findById(Integer id);
    List<Notice> findAll();
}
