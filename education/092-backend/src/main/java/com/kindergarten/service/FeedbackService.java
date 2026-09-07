package com.kindergarten.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.kindergarten.common.BusinessException;
import com.kindergarten.entity.ParentFeedback;
import com.kindergarten.mapper.ParentFeedbackMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

@Service
public class FeedbackService {

    @Autowired
    private ParentFeedbackMapper parentFeedbackMapper;

    @Autowired
    private AuthService authService;

    @Autowired
    private ChildService childService;

    public PageInfo<ParentFeedback> list(Long childId, Long userId, String role, Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        Long parentId = "parent".equals(role) ? userId : null;
        Long teacherId = "teacher".equals(role) ? userId : null;
        List<ParentFeedback> list = parentFeedbackMapper.selectList(childId, parentId, teacherId);
        return new PageInfo<>(list);
    }

    public void add(ParentFeedback entity, Long userId, String role) {
        if (!"parent".equals(role)) {
            throw new BusinessException(403, "仅家长可提交反馈");
        }
        if (entity == null || entity.getChildId() == null || !StringUtils.hasText(entity.getFeedbackContent())) {
            throw new BusinessException("幼儿档案和反馈内容不能为空");
        }
        if (!userId.equals(childService.requireChild(entity.getChildId()).getParentId())) {
            throw new BusinessException(403, "无权限提交其他幼儿反馈");
        }
        entity.setParentId(userId);
        parentFeedbackMapper.insert(entity);
    }

    public void reply(ParentFeedback entity, String role, Long userId) {
        authService.assertTeacher(role);
        if (entity == null || entity.getId() == null || !StringUtils.hasText(entity.getReplyContent())) {
            throw new BusinessException("反馈ID和回复内容不能为空");
        }
        ParentFeedback current = parentFeedbackMapper.selectById(entity.getId());
        if (current == null) {
            throw new BusinessException("家园反馈不存在");
        }
        if ("teacher".equals(role) && !userId.equals(childService.requireChild(current.getChildId()).getTeacherId())) {
            throw new BusinessException(403, "无权限回复其他班级家园反馈");
        }
        entity.setTeacherId(userId);
        parentFeedbackMapper.update(entity);
    }
}
