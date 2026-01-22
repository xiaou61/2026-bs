package com.xiaou.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.xiaou.entity.Notice;

import java.util.List;

public interface NoticeService extends IService<Notice> {
    IPage<Notice> pageNotices(Integer page, Integer size, Integer type);
    List<Notice> getLatestNotices(Integer limit);
}
