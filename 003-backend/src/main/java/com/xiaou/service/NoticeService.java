package com.xiaou.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xiaou.entity.Notice;

import java.util.List;

public interface NoticeService extends IService<Notice> {
    List<Notice> getPublishedNotices();
}

