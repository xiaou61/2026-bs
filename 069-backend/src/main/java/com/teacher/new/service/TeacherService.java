package com.teacher.new.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.teacher.new.common.BusinessException;
import com.teacher.new.common.PageResult;
import com.teacher.new.entity.SubjectInfo;
import com.teacher.new.entity.TeacherProfile;
import com.teacher.new.entity.User;
import com.teacher.new.mapper.SubjectInfoMapper;
import com.teacher.new.mapper.TeacherProfileMapper;
import com.teacher.new.mapper.UserMapper;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class TeacherService {

    @Resource
    private TeacherProfileMapper teacherProfileMapper;

    @Resource
    private UserMapper userMapper;

    @Resource
    private SubjectInfoMapper subjectInfoMapper;

    public PageResult<Map<String, Object>> page(Integer pageNum, Integer pageSize, String teacherNo, Long userId, Long subjectId, Integer status) {
        Page<TeacherProfile> page = new Page<>(pageNum, pageSize);
        LambdaQueryWrapper<TeacherProfile> wrapper = new LambdaQueryWrapper<TeacherProfile>()
                .like(StringUtils.hasText(teacherNo), TeacherProfile::getTeacherNo, teacherNo == null ? null : teacherNo.trim())
                .eq(userId != null, TeacherProfile::getUserId, userId)
                .eq(subjectId != null, TeacherProfile::getSubjectId, subjectId)
                .eq(status != null, TeacherProfile::getStatus, status)
                .orderByDesc(TeacherProfile::getId);
        Page<TeacherProfile> resultPage = teacherProfileMapper.selectPage(page, wrapper);
        List<Map<String, Object>> records = buildRecords(resultPage.getRecords());
        PageResult<Map<String, Object>> result = new PageResult<>();
        result.setTotal(resultPage.getTotal());
        result.setRecords(records);
        return result;
    }

    public List<Map<String, Object>> list() {
        List<TeacherProfile> list = teacherProfileMapper.selectList(new LambdaQueryWrapper<TeacherProfile>()
                .eq(TeacherProfile::getStatus, 1)
                .orderByAsc(TeacherProfile::getId));
        return buildRecords(list);
    }

    public void save(TeacherProfile profile) {
        if (profile == null || profile.getUserId() == null || !StringUtils.hasText(profile.getTeacherNo()) || profile.getSubjectId() == null) {
            throw new BusinessException("教师参数不完整");
        }
        if (profile.getId() == null) {
            add(profile);
        } else {
            update(profile);
        }
    }

    private void add(TeacherProfile profile) {
        User user = userMapper.selectById(profile.getUserId());
        if (user == null || !"TEACHER".equals(user.getRole())) {
            throw new BusinessException("请选择教师角色用户");
        }
        SubjectInfo subject = subjectInfoMapper.selectById(profile.getSubjectId());
        if (subject == null) {
            throw new BusinessException("科目不存在");
        }
        String teacherNo = profile.getTeacherNo().trim();
        if (teacherProfileMapper.selectOne(new LambdaQueryWrapper<TeacherProfile>().eq(TeacherProfile::getTeacherNo, teacherNo).last("limit 1")) != null) {
            throw new BusinessException("教师工号已存在");
        }
        if (teacherProfileMapper.selectOne(new LambdaQueryWrapper<TeacherProfile>().eq(TeacherProfile::getUserId, profile.getUserId()).last("limit 1")) != null) {
            throw new BusinessException("该用户已绑定教师档案");
        }
        profile.setTeacherNo(teacherNo);
        profile.setTitleName(StringUtils.hasText(profile.getTitleName()) ? profile.getTitleName().trim() : "教师");
        profile.setStatus(profile.getStatus() == null ? 1 : (profile.getStatus() == 0 ? 0 : 1));
        teacherProfileMapper.insert(profile);
    }

    private void update(TeacherProfile profile) {
        TeacherProfile db = teacherProfileMapper.selectById(profile.getId());
        if (db == null) {
            throw new BusinessException("教师档案不存在");
        }
        User user = userMapper.selectById(profile.getUserId());
        if (user == null || !"TEACHER".equals(user.getRole())) {
            throw new BusinessException("请选择教师角色用户");
        }
        SubjectInfo subject = subjectInfoMapper.selectById(profile.getSubjectId());
        if (subject == null) {
            throw new BusinessException("科目不存在");
        }
        String teacherNo = profile.getTeacherNo().trim();
        TeacherProfile noExist = teacherProfileMapper.selectOne(new LambdaQueryWrapper<TeacherProfile>()
                .eq(TeacherProfile::getTeacherNo, teacherNo)
                .last("limit 1"));
        if (noExist != null && !noExist.getId().equals(profile.getId())) {
            throw new BusinessException("教师工号已存在");
        }
        TeacherProfile userExist = teacherProfileMapper.selectOne(new LambdaQueryWrapper<TeacherProfile>()
                .eq(TeacherProfile::getUserId, profile.getUserId())
                .last("limit 1"));
        if (userExist != null && !userExist.getId().equals(profile.getId())) {
            throw new BusinessException("该用户已绑定教师档案");
        }
        db.setUserId(profile.getUserId());
        db.setTeacherNo(teacherNo);
        db.setSubjectId(profile.getSubjectId());
        db.setTitleName(StringUtils.hasText(profile.getTitleName()) ? profile.getTitleName().trim() : "教师");
        if (profile.getStatus() != null) {
            db.setStatus(profile.getStatus() == 0 ? 0 : 1);
        }
        teacherProfileMapper.updateById(db);
    }

    public void updateStatus(Long id, Integer status) {
        if (status == null || (status != 0 && status != 1)) {
            throw new BusinessException("状态不合法");
        }
        TeacherProfile db = teacherProfileMapper.selectById(id);
        if (db == null) {
            throw new BusinessException("教师档案不存在");
        }
        db.setStatus(status);
        teacherProfileMapper.updateById(db);
    }

    public void deleteById(Long id) {
        teacherProfileMapper.deleteById(id);
    }

    public TeacherProfile getByUserId(Long userId) {
        if (userId == null) {
            return null;
        }
        return teacherProfileMapper.selectOne(new LambdaQueryWrapper<TeacherProfile>()
                .eq(TeacherProfile::getUserId, userId)
                .last("limit 1"));
    }

    public Long countAll() {
        return teacherProfileMapper.selectCount(null);
    }

    public Map<Long, TeacherProfile> mapByIds(Collection<Long> ids) {
        Map<Long, TeacherProfile> map = new HashMap<>();
        if (ids == null || ids.isEmpty()) {
            return map;
        }
        Set<Long> idSet = ids.stream().filter(java.util.Objects::nonNull).collect(Collectors.toSet());
        if (idSet.isEmpty()) {
            return map;
        }
        List<TeacherProfile> list = teacherProfileMapper.selectBatchIds(idSet);
        for (TeacherProfile item : list) {
            map.put(item.getId(), item);
        }
        return map;
    }

    private List<Map<String, Object>> buildRecords(List<TeacherProfile> list) {
        List<Map<String, Object>> records = new ArrayList<>();
        if (list.isEmpty()) {
            return records;
        }
        Set<Long> userIds = list.stream().map(TeacherProfile::getUserId).collect(Collectors.toSet());
        Set<Long> subjectIds = list.stream().map(TeacherProfile::getSubjectId).collect(Collectors.toSet());
        Map<Long, User> userMap = userMapper.selectBatchIds(userIds).stream().collect(Collectors.toMap(User::getId, e -> e));
        Map<Long, SubjectInfo> subjectMap = subjectInfoMapper.selectBatchIds(subjectIds).stream().collect(Collectors.toMap(SubjectInfo::getId, e -> e));
        for (TeacherProfile item : list) {
            User user = userMap.get(item.getUserId());
            SubjectInfo subject = subjectMap.get(item.getSubjectId());
            Map<String, Object> row = new LinkedHashMap<>();
            row.put("id", item.getId());
            row.put("userId", item.getUserId());
            row.put("teacherNo", item.getTeacherNo());
            row.put("subjectId", item.getSubjectId());
            row.put("titleName", item.getTitleName());
            row.put("status", item.getStatus());
            row.put("teacherName", user == null ? "" : (StringUtils.hasText(user.getNickname()) ? user.getNickname() : user.getUsername()));
            row.put("subjectName", subject == null ? "" : subject.getSubjectName());
            row.put("createTime", item.getCreateTime());
            records.add(row);
        }
        return records;
    }
}
