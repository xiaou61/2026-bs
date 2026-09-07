package com.xiaou.community.service;

import com.xiaou.community.entity.Notice;
import java.util.List;

public interface NoticeService {
    void add(Notice notice);
    void update(Notice notice);
    void delete(Integer id);
    Notice getById(Integer id);
    List<Notice> getAll();
}
