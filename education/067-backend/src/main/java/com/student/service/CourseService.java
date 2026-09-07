package com.student.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.student.common.BusinessException;
import com.student.common.PageResult;
import com.student.entity.Course;
import com.student.entity.User;
import com.student.mapper.CourseMapper;
import com.student.mapper.EnrollMapper;
import com.student.mapper.UserMapper;
import com.student.vo.CourseVO;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class CourseService {

    @Resource
    private CourseMapper courseMapper;

    @Resource
    private UserMapper userMapper;

    @Resource
    private EnrollMapper enrollMapper;

    public PageResult<CourseVO> page(Integer pageNum, Integer pageSize, String name, Integer status, Long teacherId, String role, Long userId) {
        Long finalTeacherId = teacherId;
        if ("TEACHER".equals(role)) {
            finalTeacherId = userId;
        }
        PageHelper.startPage(pageNum, pageSize);
        List<Course> list = courseMapper.selectPageList(name, finalTeacherId, status);
        PageInfo<Course> pageInfo = new PageInfo<>(list);
        PageResult<CourseVO> result = new PageResult<>();
        result.setTotal(pageInfo.getTotal());
        result.setRecords(convertList(pageInfo.getList()));
        return result;
    }

    public List<CourseVO> list(Integer status) {
        return convertList(courseMapper.selectList(status));
    }

    public Course mustGetById(Long id) {
        Course course = courseMapper.selectById(id);
        if (course == null) {
            throw new BusinessException("课程不存在");
        }
        return course;
    }

    public void save(Course course, String role, Long userId) {
        if (course == null) {
            throw new BusinessException("课程参数不能为空");
        }
        if (course.getName() == null || course.getName().trim().isEmpty()) {
            throw new BusinessException("课程名称不能为空");
        }
        course.setName(course.getName().trim());
        if (course.getCredit() == null) {
            course.setCredit(new BigDecimal("2.0"));
        }
        if (course.getCredit().compareTo(BigDecimal.ZERO) <= 0) {
            throw new BusinessException("学分必须大于0");
        }
        if (course.getMaxStudent() == null || course.getMaxStudent() <= 0) {
            course.setMaxStudent(60);
        }
        course.setLocation(course.getLocation() == null ? "" : course.getLocation().trim());
        course.setDescription(course.getDescription() == null ? "" : course.getDescription().trim());
        course.setStatus(normalizeStatus(course.getStatus(), 1));
        if (course.getId() == null) {
            add(course, role, userId);
        } else {
            update(course, role, userId);
        }
    }

    private void add(Course course, String role, Long userId) {
        if ("TEACHER".equals(role)) {
            course.setTeacherId(userId);
        }
        if (course.getTeacherId() == null) {
            throw new BusinessException("请选择授课教师");
        }
        User teacher = userMapper.selectById(course.getTeacherId());
        if (teacher == null || teacher.getStatus() == null || teacher.getStatus() == 0 || (!"TEACHER".equals(teacher.getRole()) && !"ADMIN".equals(teacher.getRole()))) {
            throw new BusinessException("授课教师不存在");
        }
        course.setSelectedCount(0);
        courseMapper.insert(course);
    }

    private void update(Course course, String role, Long userId) {
        Course db = mustGetById(course.getId());
        if ("TEACHER".equals(role) && !userId.equals(db.getTeacherId())) {
            throw new BusinessException("无权限修改该课程");
        }
        if ("TEACHER".equals(role)) {
            course.setTeacherId(userId);
        }
        if (course.getTeacherId() == null) {
            course.setTeacherId(db.getTeacherId());
        }
        User teacher = userMapper.selectById(course.getTeacherId());
        if (teacher == null || teacher.getStatus() == null || teacher.getStatus() == 0 || (!"TEACHER".equals(teacher.getRole()) && !"ADMIN".equals(teacher.getRole()))) {
            throw new BusinessException("授课教师不存在");
        }
        Integer selectedCount = db.getSelectedCount() == null ? 0 : db.getSelectedCount();
        if (course.getMaxStudent() < selectedCount) {
            throw new BusinessException("人数上限不能小于已选人数");
        }
        course.setSelectedCount(selectedCount);
        courseMapper.update(course);
    }

    public void deleteById(Long id, String role, Long userId) {
        Course db = mustGetById(id);
        if ("TEACHER".equals(role) && !userId.equals(db.getTeacherId())) {
            throw new BusinessException("无权限删除该课程");
        }
        courseMapper.deleteById(id);
        enrollMapper.deleteByCourseId(id);
    }

    public Long countAll() {
        Long count = courseMapper.countAll();
        return count == null ? 0L : count;
    }

    public void increaseSelectedCount(Long courseId, int delta) {
        Course db = mustGetById(courseId);
        int current = db.getSelectedCount() == null ? 0 : db.getSelectedCount();
        int target = Math.max(0, current + delta);
        if (target > (db.getMaxStudent() == null ? 0 : db.getMaxStudent())) {
            throw new BusinessException("课程人数已满");
        }
        courseMapper.updateSelectedCount(courseId, target);
    }

    private Integer normalizeStatus(Integer status, Integer fallback) {
        Integer s = status == null ? fallback : status;
        if (s == null) {
            s = 1;
        }
        if (s < 0 || s > 2) {
            throw new BusinessException("课程状态不合法");
        }
        return s;
    }

    private List<CourseVO> convertList(List<Course> list) {
        if (list == null || list.isEmpty()) {
            return new ArrayList<>();
        }
        Map<Long, String> userMap = buildUserMap();
        List<CourseVO> result = new ArrayList<>();
        for (Course item : list) {
            CourseVO vo = new CourseVO();
            BeanUtils.copyProperties(item, vo);
            vo.setTeacherName(userMap.getOrDefault(item.getTeacherId(), ""));
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
