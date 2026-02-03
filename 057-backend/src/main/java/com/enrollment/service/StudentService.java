package com.enrollment.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.enrollment.entity.Student;
import com.enrollment.mapper.StudentMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudentService {

    @Autowired
    private StudentMapper studentMapper;

    public IPage<Student> getPage(Integer pageNum, Integer pageSize, String name, String examNo, Integer status) {
        Page<Student> page = new Page<>(pageNum, pageSize);
        QueryWrapper<Student> wrapper = new QueryWrapper<>();
        if (name != null && !name.isEmpty()) {
            wrapper.like("name", name);
        }
        if (examNo != null && !examNo.isEmpty()) {
            wrapper.like("exam_no", examNo);
        }
        if (status != null) {
            wrapper.eq("status", status);
        }
        wrapper.orderByDesc("create_time");
        return studentMapper.selectPage(page, wrapper);
    }

    public Student getById(Long id) {
        return studentMapper.selectById(id);
    }

    public List<Student> getAll() {
        return studentMapper.selectList(null);
    }

    public void add(Student student) {
        studentMapper.insert(student);
    }

    public void update(Student student) {
        studentMapper.updateById(student);
    }

    public void delete(Long id) {
        studentMapper.deleteById(id);
    }

    public void audit(Long id, Integer status) {
        Student student = new Student();
        student.setId(id);
        student.setStatus(status);
        studentMapper.updateById(student);
    }
}
