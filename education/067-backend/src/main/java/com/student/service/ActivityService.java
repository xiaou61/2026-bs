package com.student.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.student.common.BusinessException;
import com.student.common.PageResult;
import com.student.entity.Activity;
import com.student.entity.User;
import com.student.mapper.ActivityMapper;
import com.student.mapper.SignupMapper;
import com.student.mapper.UserMapper;
import com.student.vo.ActivityVO;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ActivityService {

    @Resource
    private ActivityMapper activityMapper;

    @Resource
    private SignupMapper signupMapper;

    @Resource
    private UserMapper userMapper;

    public PageResult<ActivityVO> page(Integer pageNum, Integer pageSize, String title, Integer status, Long organizerId, String role, Long userId) {
        Long finalOrganizerId = organizerId;
        if ("TEACHER".equals(role)) {
            finalOrganizerId = userId;
        }
        PageHelper.startPage(pageNum, pageSize);
        List<Activity> list = activityMapper.selectPageList(title, finalOrganizerId, status);
        PageInfo<Activity> pageInfo = new PageInfo<>(list);
        PageResult<ActivityVO> result = new PageResult<>();
        result.setTotal(pageInfo.getTotal());
        result.setRecords(convertList(pageInfo.getList()));
        return result;
    }

    public List<ActivityVO> list(Integer status) {
        return convertList(activityMapper.selectList(status));
    }

    public Activity mustGetById(Long id) {
        Activity activity = activityMapper.selectById(id);
        if (activity == null) {
            throw new BusinessException("活动不存在");
        }
        return activity;
    }

    public void save(Activity activity, String role, Long userId) {
        if (activity == null) {
            throw new BusinessException("活动参数不能为空");
        }
        if (activity.getTitle() == null || activity.getTitle().trim().isEmpty()) {
            throw new BusinessException("活动标题不能为空");
        }
        activity.setTitle(activity.getTitle().trim());
        if (activity.getMaxParticipant() == null || activity.getMaxParticipant() <= 0) {
            activity.setMaxParticipant(200);
        }
        activity.setLocation(activity.getLocation() == null ? "" : activity.getLocation().trim());
        activity.setContent(activity.getContent() == null ? "" : activity.getContent().trim());
        activity.setStatus(normalizeStatus(activity.getStatus(), 1));
        if (activity.getId() == null) {
            add(activity, role, userId);
        } else {
            update(activity, role, userId);
        }
    }

    private void add(Activity activity, String role, Long userId) {
        if ("TEACHER".equals(role)) {
            activity.setOrganizerId(userId);
        }
        if (activity.getOrganizerId() == null) {
            throw new BusinessException("请选择组织者");
        }
        User organizer = userMapper.selectById(activity.getOrganizerId());
        if (organizer == null || organizer.getStatus() == null || organizer.getStatus() == 0 || (!"TEACHER".equals(organizer.getRole()) && !"ADMIN".equals(organizer.getRole()))) {
            throw new BusinessException("组织者不存在");
        }
        activity.setParticipantCount(0);
        activityMapper.insert(activity);
    }

    private void update(Activity activity, String role, Long userId) {
        Activity db = mustGetById(activity.getId());
        if ("TEACHER".equals(role) && !userId.equals(db.getOrganizerId())) {
            throw new BusinessException("无权限修改该活动");
        }
        if ("TEACHER".equals(role)) {
            activity.setOrganizerId(userId);
        }
        if (activity.getOrganizerId() == null) {
            activity.setOrganizerId(db.getOrganizerId());
        }
        User organizer = userMapper.selectById(activity.getOrganizerId());
        if (organizer == null || organizer.getStatus() == null || organizer.getStatus() == 0 || (!"TEACHER".equals(organizer.getRole()) && !"ADMIN".equals(organizer.getRole()))) {
            throw new BusinessException("组织者不存在");
        }
        Integer participantCount = db.getParticipantCount() == null ? 0 : db.getParticipantCount();
        if (activity.getMaxParticipant() < participantCount) {
            throw new BusinessException("人数上限不能小于已报名人数");
        }
        activity.setParticipantCount(participantCount);
        activityMapper.update(activity);
    }

    public void deleteById(Long id, String role, Long userId) {
        Activity db = mustGetById(id);
        if ("TEACHER".equals(role) && !userId.equals(db.getOrganizerId())) {
            throw new BusinessException("无权限删除该活动");
        }
        activityMapper.deleteById(id);
        signupMapper.deleteByActivityId(id);
    }

    public void increaseParticipantCount(Long activityId, int delta) {
        Activity db = mustGetById(activityId);
        int current = db.getParticipantCount() == null ? 0 : db.getParticipantCount();
        int target = Math.max(0, current + delta);
        if (target > (db.getMaxParticipant() == null ? 0 : db.getMaxParticipant())) {
            throw new BusinessException("活动人数已满");
        }
        activityMapper.updateParticipantCount(activityId, target);
    }

    public Long countAll() {
        Long count = activityMapper.countAll();
        return count == null ? 0L : count;
    }

    private Integer normalizeStatus(Integer status, Integer fallback) {
        Integer s = status == null ? fallback : status;
        if (s == null) {
            s = 1;
        }
        if (s < 0 || s > 3) {
            throw new BusinessException("活动状态不合法");
        }
        return s;
    }

    private List<ActivityVO> convertList(List<Activity> list) {
        if (list == null || list.isEmpty()) {
            return new ArrayList<>();
        }
        Map<Long, String> userMap = buildUserMap();
        List<ActivityVO> result = new ArrayList<>();
        for (Activity item : list) {
            ActivityVO vo = new ActivityVO();
            BeanUtils.copyProperties(item, vo);
            vo.setOrganizerName(userMap.getOrDefault(item.getOrganizerId(), ""));
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
