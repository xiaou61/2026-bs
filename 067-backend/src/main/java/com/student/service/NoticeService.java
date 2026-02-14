package com.student.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.student.common.BusinessException;
import com.student.common.PageResult;
import com.student.entity.Notice;
import com.student.entity.User;
import com.student.mapper.NoticeMapper;
import com.student.mapper.UserMapper;
import com.student.vo.NoticeVO;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class NoticeService {

    @Resource
    private NoticeMapper noticeMapper;

    @Resource
    private UserMapper userMapper;

    public List<NoticeVO> list() {
        return convertList(noticeMapper.selectEnabledList());
    }

    public PageResult<NoticeVO> page(Integer pageNum, Integer pageSize, String title, Integer status) {
        PageHelper.startPage(pageNum, pageSize);
        List<Notice> list = noticeMapper.selectPageList(title, status);
        PageInfo<Notice> pageInfo = new PageInfo<>(list);
        PageResult<NoticeVO> result = new PageResult<>();
        result.setTotal(pageInfo.getTotal());
        result.setRecords(convertList(pageInfo.getList()));
        return result;
    }

    public void save(Notice notice, String role, Long userId) {
        if (notice == null) {
            throw new BusinessException("公告参数不能为空");
        }
        if (notice.getTitle() == null || notice.getTitle().trim().isEmpty()) {
            throw new BusinessException("公告标题不能为空");
        }
        if (notice.getContent() == null || notice.getContent().trim().isEmpty()) {
            throw new BusinessException("公告内容不能为空");
        }
        notice.setTitle(notice.getTitle().trim());
        notice.setContent(notice.getContent().trim());
        notice.setStatus(normalizeStatus(notice.getStatus(), 1));
        if (notice.getId() == null) {
            notice.setPublisherId(userId);
            noticeMapper.insert(notice);
        } else {
            Notice db = noticeMapper.selectById(notice.getId());
            if (db == null) {
                throw new BusinessException("公告不存在");
            }
            if ("TEACHER".equals(role) && !userId.equals(db.getPublisherId())) {
                throw new BusinessException("无权限修改该公告");
            }
            notice.setPublisherId("ADMIN".equals(role) ? (notice.getPublisherId() == null ? db.getPublisherId() : notice.getPublisherId()) : db.getPublisherId());
            noticeMapper.update(notice);
        }
    }

    public void deleteById(Long id, String role, Long userId) {
        Notice db = noticeMapper.selectById(id);
        if (db == null) {
            return;
        }
        if ("TEACHER".equals(role) && !userId.equals(db.getPublisherId())) {
            throw new BusinessException("无权限删除该公告");
        }
        noticeMapper.deleteById(id);
    }

    public Long countAll() {
        Long count = noticeMapper.countAll();
        return count == null ? 0L : count;
    }

    private Integer normalizeStatus(Integer status, Integer fallback) {
        Integer s = status == null ? fallback : status;
        if (s == null) {
            s = 1;
        }
        if (s != 0 && s != 1) {
            throw new BusinessException("公告状态不合法");
        }
        return s;
    }

    private List<NoticeVO> convertList(List<Notice> list) {
        if (list == null || list.isEmpty()) {
            return new ArrayList<>();
        }
        Map<Long, String> userMap = buildUserMap();
        List<NoticeVO> result = new ArrayList<>();
        for (Notice item : list) {
            NoticeVO vo = new NoticeVO();
            BeanUtils.copyProperties(item, vo);
            vo.setPublisherName(userMap.getOrDefault(item.getPublisherId(), ""));
            result.add(vo);
        }
        return result;
    }

    private Map<Long, String> buildUserMap() {
        List<User> users = userMapper.selectPageList(null, null, null, null);
        Map<Long, String> userMap = new HashMap<>();
        for (User user : users) {
            String name = user.getNickname() == null || user.getNickname().trim().isEmpty() ? user.getUsername() : user.getNickname();
            userMap.put(user.getId(), name);
        }
        return userMap;
    }
}
