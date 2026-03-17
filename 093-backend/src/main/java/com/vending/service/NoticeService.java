package com.vending.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.vending.common.BusinessException;
import com.vending.common.PageResult;
import com.vending.entity.SystemNotice;
import com.vending.entity.User;
import com.vending.mapper.SystemNoticeMapper;
import com.vending.mapper.UserMapper;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class NoticeService {

    @Resource
    private SystemNoticeMapper noticeMapper;

    @Resource
    private UserMapper userMapper;

    public PageResult<SystemNotice> page(Integer pageNum, Integer pageSize, String title, Integer status) {
        Page<SystemNotice> page = new Page<>(pageNum, pageSize);
        LambdaQueryWrapper<SystemNotice> wrapper = new LambdaQueryWrapper<SystemNotice>()
                .like(StringUtils.hasText(title), SystemNotice::getTitle, title == null ? null : title.trim())
                .eq(status != null, SystemNotice::getStatus, status)
                .orderByDesc(SystemNotice::getId);
        Page<SystemNotice> resultPage = noticeMapper.selectPage(page, wrapper);
        fillPublisher(resultPage.getRecords());
        PageResult<SystemNotice> result = new PageResult<>();
        result.setTotal(resultPage.getTotal());
        result.setRecords(resultPage.getRecords());
        return result;
    }

    public List<SystemNotice> publicList() {
        List<SystemNotice> list = noticeMapper.selectList(new LambdaQueryWrapper<SystemNotice>()
                .eq(SystemNotice::getStatus, 1)
                .orderByDesc(SystemNotice::getId));
        fillPublisher(list);
        return list;
    }

    public void save(Long publisherId, SystemNotice notice) {
        if (notice == null || !StringUtils.hasText(notice.getTitle())) {
            throw new BusinessException("公告标题不能为空");
        }
        if (notice.getId() == null) {
            notice.setPublisherId(publisherId);
            notice.setStatus(notice.getStatus() == null ? 1 : notice.getStatus());
            noticeMapper.insert(notice);
        } else {
            SystemNotice db = noticeMapper.selectById(notice.getId());
            if (db == null) {
                throw new BusinessException("公告不存在");
            }
            db.setTitle(notice.getTitle().trim());
            db.setContent(notice.getContent());
            db.setNoticeType(StringUtils.hasText(notice.getNoticeType()) ? notice.getNoticeType().trim().toUpperCase() : db.getNoticeType());
            if (notice.getStatus() != null) {
                db.setStatus(notice.getStatus());
            }
            noticeMapper.updateById(db);
        }
    }

    public void deleteById(Long id) {
        noticeMapper.deleteById(id);
    }

    private void fillPublisher(List<SystemNotice> list) {
        if (list == null || list.isEmpty()) {
            return;
        }
        Map<Long, String> userMap = userMapper.selectList(new LambdaQueryWrapper<User>()
                        .in(User::getId, list.stream().map(SystemNotice::getPublisherId).distinct().collect(Collectors.toList())))
                .stream()
                .collect(Collectors.toMap(User::getId, User::getNickname, (a, b) -> a, HashMap::new));
        list.forEach(item -> item.setPublisherName(userMap.get(item.getPublisherId())));
    }
}
