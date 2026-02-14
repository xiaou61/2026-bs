package com.student.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.student.common.BusinessException;
import com.student.common.PageResult;
import com.student.entity.Activity;
import com.student.entity.ActivitySignup;
import com.student.entity.User;
import com.student.mapper.ActivityMapper;
import com.student.mapper.SignupMapper;
import com.student.mapper.UserMapper;
import com.student.vo.SignupVO;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class SignupService {

    @Resource
    private SignupMapper signupMapper;

    @Resource
    private ActivityMapper activityMapper;

    @Resource
    private ActivityService activityService;

    @Resource
    private UserMapper userMapper;

    public PageResult<SignupVO> page(Integer pageNum, Integer pageSize, Long activityId, Long studentId, Integer status, String role, Long userId) {
        Long organizerId = "TEACHER".equals(role) ? userId : null;
        PageHelper.startPage(pageNum, pageSize);
        List<ActivitySignup> list = signupMapper.selectPageList(activityId, studentId, status, organizerId);
        PageInfo<ActivitySignup> pageInfo = new PageInfo<>(list);
        PageResult<SignupVO> result = new PageResult<>();
        result.setTotal(pageInfo.getTotal());
        result.setRecords(convertList(pageInfo.getList()));
        return result;
    }

    public PageResult<SignupVO> myPage(Long studentId, Integer pageNum, Integer pageSize, Integer status, Long activityId) {
        PageHelper.startPage(pageNum, pageSize);
        List<ActivitySignup> list = signupMapper.selectPageList(activityId, studentId, status, null);
        PageInfo<ActivitySignup> pageInfo = new PageInfo<>(list);
        PageResult<SignupVO> result = new PageResult<>();
        result.setTotal(pageInfo.getTotal());
        result.setRecords(convertList(pageInfo.getList()));
        return result;
    }

    public void add(Long activityId, Long studentId) {
        if (activityId == null) {
            throw new BusinessException("活动不能为空");
        }
        Activity activity = activityService.mustGetById(activityId);
        if (activity.getStatus() == null || activity.getStatus() != 1) {
            throw new BusinessException("当前活动不可报名");
        }
        ActivitySignup exist = signupMapper.selectByActivityAndStudent(activityId, studentId);
        if (exist != null) {
            throw new BusinessException("你已报名该活动");
        }
        int participant = activity.getParticipantCount() == null ? 0 : activity.getParticipantCount();
        int max = activity.getMaxParticipant() == null ? 0 : activity.getMaxParticipant();
        if (participant >= max) {
            throw new BusinessException("活动人数已满");
        }
        ActivitySignup signup = new ActivitySignup();
        signup.setActivityId(activityId);
        signup.setStudentId(studentId);
        signup.setStatus(1);
        signup.setRemark("已报名");
        signupMapper.insert(signup);
        activityService.increaseParticipantCount(activityId, 1);
    }

    public void updateStatus(Long id, Integer status, String remark, String role, Long userId) {
        if (status == null || (status != 1 && status != 2 && status != 3)) {
            throw new BusinessException("状态不合法");
        }
        ActivitySignup db = signupMapper.selectById(id);
        if (db == null) {
            throw new BusinessException("报名记录不存在");
        }
        Activity activity = activityMapper.selectById(db.getActivityId());
        if (activity == null) {
            throw new BusinessException("活动不存在");
        }
        if ("TEACHER".equals(role) && !userId.equals(activity.getOrganizerId())) {
            throw new BusinessException("无权限操作该记录");
        }
        if ("STUDENT".equals(role) && !userId.equals(db.getStudentId())) {
            throw new BusinessException("无权限操作该记录");
        }
        int before = db.getStatus() == null ? 1 : db.getStatus();
        int after = status;
        if (before != after) {
            if ((before == 1 || before == 3) && after == 2) {
                activityService.increaseParticipantCount(db.getActivityId(), -1);
            }
            if (before == 2 && (after == 1 || after == 3)) {
                activityService.increaseParticipantCount(db.getActivityId(), 1);
            }
        }
        db.setStatus(after);
        db.setRemark(remark == null ? "" : remark.trim());
        if (after == 3) {
            db.setCheckinTime(LocalDateTime.now());
        }
        signupMapper.updateStatus(db);
    }

    public void deleteById(Long id, String role, Long userId) {
        ActivitySignup db = signupMapper.selectById(id);
        if (db == null) {
            return;
        }
        Activity activity = activityMapper.selectById(db.getActivityId());
        if ("STUDENT".equals(role) && !userId.equals(db.getStudentId())) {
            throw new BusinessException("无权限删除该记录");
        }
        if ("TEACHER".equals(role) && (activity == null || !userId.equals(activity.getOrganizerId()))) {
            throw new BusinessException("无权限删除该记录");
        }
        if (db.getStatus() != null && (db.getStatus() == 1 || db.getStatus() == 3)) {
            activityService.increaseParticipantCount(db.getActivityId(), -1);
        }
        signupMapper.deleteById(id);
    }

    public Long countAll() {
        Long count = signupMapper.countAll();
        return count == null ? 0L : count;
    }

    private List<SignupVO> convertList(List<ActivitySignup> list) {
        if (list == null || list.isEmpty()) {
            return new ArrayList<>();
        }
        Map<Long, Activity> activityMap = new HashMap<>();
        for (Activity activity : activityMapper.selectList(null)) {
            activityMap.put(activity.getId(), activity);
        }
        Map<Long, String> userMap = buildUserMap();
        List<SignupVO> result = new ArrayList<>();
        for (ActivitySignup item : list) {
            SignupVO vo = new SignupVO();
            BeanUtils.copyProperties(item, vo);
            Activity activity = activityMap.get(item.getActivityId());
            vo.setActivityTitle(activity == null ? "" : activity.getTitle());
            vo.setStudentName(userMap.getOrDefault(item.getStudentId(), ""));
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
