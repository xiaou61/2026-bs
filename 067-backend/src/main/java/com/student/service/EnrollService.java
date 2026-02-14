package com.student.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.student.common.BusinessException;
import com.student.common.PageResult;
import com.student.entity.Course;
import com.student.entity.CourseEnroll;
import com.student.entity.User;
import com.student.mapper.CourseMapper;
import com.student.mapper.EnrollMapper;
import com.student.mapper.UserMapper;
import com.student.vo.EnrollVO;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class EnrollService {

    @Resource
    private EnrollMapper enrollMapper;

    @Resource
    private CourseMapper courseMapper;

    @Resource
    private CourseService courseService;

    @Resource
    private UserMapper userMapper;

    public PageResult<EnrollVO> page(Integer pageNum, Integer pageSize, Long courseId, Long studentId, Integer status, String role, Long userId) {
        Long teacherId = "TEACHER".equals(role) ? userId : null;
        PageHelper.startPage(pageNum, pageSize);
        List<CourseEnroll> list = enrollMapper.selectPageList(courseId, studentId, status, teacherId);
        PageInfo<CourseEnroll> pageInfo = new PageInfo<>(list);
        PageResult<EnrollVO> result = new PageResult<>();
        result.setTotal(pageInfo.getTotal());
        result.setRecords(convertList(pageInfo.getList()));
        return result;
    }

    public PageResult<EnrollVO> myPage(Long studentId, Integer pageNum, Integer pageSize, Integer status, Long courseId) {
        PageHelper.startPage(pageNum, pageSize);
        List<CourseEnroll> list = enrollMapper.selectPageList(courseId, studentId, status, null);
        PageInfo<CourseEnroll> pageInfo = new PageInfo<>(list);
        PageResult<EnrollVO> result = new PageResult<>();
        result.setTotal(pageInfo.getTotal());
        result.setRecords(convertList(pageInfo.getList()));
        return result;
    }

    public void add(Long courseId, Long studentId) {
        if (courseId == null) {
            throw new BusinessException("课程不能为空");
        }
        Course course = courseService.mustGetById(courseId);
        if (course.getStatus() == null || course.getStatus() != 1) {
            throw new BusinessException("当前课程不可选");
        }
        CourseEnroll exist = enrollMapper.selectByCourseAndStudent(courseId, studentId);
        if (exist != null) {
            throw new BusinessException("你已选过该课程");
        }
        int selected = course.getSelectedCount() == null ? 0 : course.getSelectedCount();
        int max = course.getMaxStudent() == null ? 0 : course.getMaxStudent();
        if (selected >= max) {
            throw new BusinessException("课程人数已满");
        }
        CourseEnroll enroll = new CourseEnroll();
        enroll.setCourseId(courseId);
        enroll.setStudentId(studentId);
        enroll.setStatus(1);
        enroll.setRemark("正常选课");
        enrollMapper.insert(enroll);
        courseService.increaseSelectedCount(courseId, 1);
    }

    public void updateStatus(Long id, Integer status, java.math.BigDecimal score, String remark, String role, Long userId) {
        if (status == null || (status != 1 && status != 2)) {
            throw new BusinessException("状态不合法");
        }
        CourseEnroll db = enrollMapper.selectById(id);
        if (db == null) {
            throw new BusinessException("选课记录不存在");
        }
        Course course = courseService.mustGetById(db.getCourseId());
        if ("TEACHER".equals(role) && !userId.equals(course.getTeacherId())) {
            throw new BusinessException("无权限操作该记录");
        }
        int before = db.getStatus() == null ? 1 : db.getStatus();
        int after = status;
        if (before != after) {
            if (before == 1 && after == 2) {
                courseService.increaseSelectedCount(db.getCourseId(), -1);
            }
            if (before == 2 && after == 1) {
                courseService.increaseSelectedCount(db.getCourseId(), 1);
            }
        }
        db.setStatus(after);
        db.setScore(score);
        db.setRemark(remark == null ? "" : remark.trim());
        enrollMapper.updateStatus(db);
    }

    public void deleteById(Long id, String role, Long userId) {
        CourseEnroll db = enrollMapper.selectById(id);
        if (db == null) {
            return;
        }
        Course course = courseMapper.selectById(db.getCourseId());
        if ("STUDENT".equals(role) && !userId.equals(db.getStudentId())) {
            throw new BusinessException("无权限删除该记录");
        }
        if ("TEACHER".equals(role) && (course == null || !userId.equals(course.getTeacherId()))) {
            throw new BusinessException("无权限删除该记录");
        }
        if (db.getStatus() != null && db.getStatus() == 1) {
            courseService.increaseSelectedCount(db.getCourseId(), -1);
        }
        enrollMapper.deleteById(id);
    }

    public Long countAll() {
        Long count = enrollMapper.countAll();
        return count == null ? 0L : count;
    }

    private List<EnrollVO> convertList(List<CourseEnroll> list) {
        if (list == null || list.isEmpty()) {
            return new ArrayList<>();
        }
        Map<Long, Course> courseMap = new HashMap<>();
        for (Course course : courseMapper.selectList(null)) {
            courseMap.put(course.getId(), course);
        }
        Map<Long, String> userMap = buildUserMap();
        List<EnrollVO> result = new ArrayList<>();
        for (CourseEnroll item : list) {
            EnrollVO vo = new EnrollVO();
            BeanUtils.copyProperties(item, vo);
            Course course = courseMap.get(item.getCourseId());
            if (course != null) {
                vo.setCourseName(course.getName());
                vo.setTeacherName(userMap.getOrDefault(course.getTeacherId(), ""));
            } else {
                vo.setCourseName("");
                vo.setTeacherName("");
            }
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
