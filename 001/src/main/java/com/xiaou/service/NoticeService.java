package com.xiaou.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.xiaou.entity.Notice;

/**
 * 公告Service接口
 * @author xiaou
 */
public interface NoticeService extends IService<Notice> {
    
    /**
     * 发布公告
     */
    void publishNotice(Notice notice);
    
    /**
     * 分页查询公告列表
     */
    Page<Notice> getNoticePage(int pageNum, int pageSize, String category, String keyword);
    
    /**
     * 增加浏览次数
     */
    void incrementViewCount(Long id);
    
    /**
     * 置顶/取消置顶
     */
    void toggleTop(Long id);
}

