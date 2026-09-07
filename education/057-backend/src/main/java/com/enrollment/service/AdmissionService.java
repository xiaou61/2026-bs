package com.enrollment.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.enrollment.entity.Admission;
import com.enrollment.entity.Student;
import com.enrollment.mapper.AdmissionMapper;
import com.enrollment.mapper.StudentMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class AdmissionService {

    @Autowired
    private AdmissionMapper admissionMapper;

    @Autowired
    private StudentMapper studentMapper;

    public IPage<Admission> getPage(Integer pageNum, Integer pageSize, String studentName, Integer status) {
        Page<Admission> page = new Page<>(pageNum, pageSize);
        return admissionMapper.selectPageWithStudent(page, studentName, status);
    }

    public void add(Admission admission) {
        admission.setAdmitTime(LocalDateTime.now());
        admission.setAdmissionNo(generateAdmissionNo());
        admissionMapper.insert(admission);
        Student student = new Student();
        student.setId(admission.getStudentId());
        student.setStatus(2);
        studentMapper.updateById(student);
    }

    public void update(Admission admission) {
        admissionMapper.updateById(admission);
    }

    public void delete(Long id) {
        admissionMapper.deleteById(id);
    }

    public void confirm(Long id) {
        Admission admission = new Admission();
        admission.setId(id);
        admission.setStatus(1);
        admission.setConfirmTime(LocalDateTime.now());
        admissionMapper.updateById(admission);
    }

    public void batchAdmit(List<Admission> admissions) {
        for (Admission admission : admissions) {
            add(admission);
        }
    }

    private String generateAdmissionNo() {
        return "AD" + System.currentTimeMillis();
    }
}
