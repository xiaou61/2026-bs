package com.alumni.service;

import com.alumni.entity.*;
import com.alumni.mapper.*;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class AlumniService {

    @Autowired
    private AlumniInfoMapper alumniInfoMapper;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private GradeMapper gradeMapper;

    @Autowired
    private ClassInfoMapper classInfoMapper;

    public Page<AlumniInfo> list(Integer pageNum, Integer pageSize, String name, Long gradeId, Long classId, String company, String city) {
        Page<AlumniInfo> page = new Page<>(pageNum, pageSize);
        LambdaQueryWrapper<AlumniInfo> wrapper = new LambdaQueryWrapper<>();
        if (gradeId != null) {
            wrapper.eq(AlumniInfo::getGradeId, gradeId);
        }
        if (classId != null) {
            wrapper.eq(AlumniInfo::getClassId, classId);
        }
        if (StringUtils.hasText(company)) {
            wrapper.like(AlumniInfo::getCompany, company);
        }
        if (StringUtils.hasText(city)) {
            wrapper.like(AlumniInfo::getCity, city);
        }
        wrapper.orderByDesc(AlumniInfo::getCreateTime);
        Page<AlumniInfo> result = alumniInfoMapper.selectPage(page, wrapper);
        fillAlumniInfo(result.getRecords(), name);
        return result;
    }

    private void fillAlumniInfo(List<AlumniInfo> list, String filterName) {
        List<User> users = userMapper.selectList(null);
        Map<Long, User> userMap = users.stream().collect(Collectors.toMap(User::getId, u -> u));
        List<Grade> grades = gradeMapper.selectList(null);
        Map<Long, String> gradeMap = grades.stream().collect(Collectors.toMap(Grade::getId, Grade::getName));
        List<ClassInfo> classes = classInfoMapper.selectList(null);
        Map<Long, String> classMap = classes.stream().collect(Collectors.toMap(ClassInfo::getId, ClassInfo::getName));
        list.removeIf(a -> {
            User user = userMap.get(a.getUserId());
            if (user == null) return true;
            if (StringUtils.hasText(filterName) && !user.getName().contains(filterName)) return true;
            a.setUserName(user.getName());
            a.setUserAvatar(user.getAvatar());
            a.setUserPhone(user.getPhone());
            a.setUserEmail(user.getEmail());
            a.setGradeName(gradeMap.get(a.getGradeId()));
            a.setClassName(classMap.get(a.getClassId()));
            return false;
        });
    }

    public AlumniInfo getById(Long id) {
        AlumniInfo info = alumniInfoMapper.selectById(id);
        if (info != null) {
            User user = userMapper.selectById(info.getUserId());
            if (user != null) {
                info.setUserName(user.getName());
                info.setUserAvatar(user.getAvatar());
                info.setUserPhone(user.getPhone());
                info.setUserEmail(user.getEmail());
            }
            Grade grade = gradeMapper.selectById(info.getGradeId());
            if (grade != null) info.setGradeName(grade.getName());
            ClassInfo classInfo = classInfoMapper.selectById(info.getClassId());
            if (classInfo != null) info.setClassName(classInfo.getName());
        }
        return info;
    }

    public AlumniInfo getByUserId(Long userId) {
        return alumniInfoMapper.selectOne(new LambdaQueryWrapper<AlumniInfo>().eq(AlumniInfo::getUserId, userId));
    }

    public void saveOrUpdate(AlumniInfo info) {
        if (info.getId() != null) {
            alumniInfoMapper.updateById(info);
        } else {
            AlumniInfo exist = alumniInfoMapper.selectOne(new LambdaQueryWrapper<AlumniInfo>().eq(AlumniInfo::getUserId, info.getUserId()));
            if (exist != null) {
                info.setId(exist.getId());
                alumniInfoMapper.updateById(info);
            } else {
                alumniInfoMapper.insert(info);
            }
        }
    }

    public List<AlumniInfo> contacts(Long gradeId, Long classId) {
        LambdaQueryWrapper<AlumniInfo> wrapper = new LambdaQueryWrapper<>();
        if (gradeId != null) {
            wrapper.eq(AlumniInfo::getGradeId, gradeId);
        }
        if (classId != null) {
            wrapper.eq(AlumniInfo::getClassId, classId);
        }
        List<AlumniInfo> list = alumniInfoMapper.selectList(wrapper);
        fillAlumniInfo(list, null);
        return list;
    }

    public Map<String, Object> distribution() {
        List<AlumniInfo> all = alumniInfoMapper.selectList(null);
        Map<String, Long> cityDist = all.stream()
                .filter(a -> StringUtils.hasText(a.getCity()))
                .collect(Collectors.groupingBy(AlumniInfo::getCity, Collectors.counting()));
        Map<String, Long> industryDist = all.stream()
                .filter(a -> StringUtils.hasText(a.getIndustry()))
                .collect(Collectors.groupingBy(AlumniInfo::getIndustry, Collectors.counting()));
        Map<String, Object> result = new HashMap<>();
        result.put("cityDistribution", cityDist);
        result.put("industryDistribution", industryDist);
        return result;
    }
}
