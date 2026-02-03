package com.enrollment.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.enrollment.entity.*;
import com.enrollment.mapper.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class StatsService {

    @Autowired
    private StudentMapper studentMapper;

    @Autowired
    private ApplicationMapper applicationMapper;

    @Autowired
    private AdmissionMapper admissionMapper;

    @Autowired
    private EnrollmentPlanMapper planMapper;

    @Autowired
    private MajorMapper majorMapper;

    @Autowired
    private DepartmentMapper departmentMapper;

    public Map<String, Object> getDashboard() {
        Map<String, Object> result = new HashMap<>();
        result.put("studentCount", studentMapper.selectCount(null));
        result.put("applicationCount", applicationMapper.selectCount(null));
        result.put("admissionCount", admissionMapper.selectCount(null));
        result.put("planCount", planMapper.selectCount(null));
        result.put("majorCount", majorMapper.selectCount(null));
        result.put("departmentCount", departmentMapper.selectCount(null));
        result.put("pendingApplication", applicationMapper.selectCount(new QueryWrapper<Application>().eq("status", 0)));
        result.put("pendingAdmission", admissionMapper.selectCount(new QueryWrapper<Admission>().eq("status", 0)));
        return result;
    }

    public Map<String, Object> getApplicationStats() {
        Map<String, Object> result = new HashMap<>();
        List<Application> applications = applicationMapper.selectList(null);
        long total = applications.size();
        long pending = applications.stream().filter(a -> a.getStatus() == 0).count();
        long approved = applications.stream().filter(a -> a.getStatus() == 1).count();
        long rejected = applications.stream().filter(a -> a.getStatus() == 2).count();
        result.put("total", total);
        result.put("pending", pending);
        result.put("approved", approved);
        result.put("rejected", rejected);
        return result;
    }

    public Map<String, Object> getAdmissionStats() {
        Map<String, Object> result = new HashMap<>();
        List<Admission> admissions = admissionMapper.selectList(null);
        long total = admissions.size();
        long pending = admissions.stream().filter(a -> a.getStatus() == 0).count();
        long confirmed = admissions.stream().filter(a -> a.getStatus() == 1).count();
        long abandoned = admissions.stream().filter(a -> a.getStatus() == 2).count();
        result.put("total", total);
        result.put("pending", pending);
        result.put("confirmed", confirmed);
        result.put("abandoned", abandoned);
        return result;
    }

    public List<Map<String, Object>> getMajorStats() {
        List<Major> majors = majorMapper.selectList(null);
        List<EnrollmentPlan> plans = planMapper.selectList(null);
        List<Map<String, Object>> result = new ArrayList<>();
        for (Major major : majors) {
            Map<String, Object> item = new HashMap<>();
            item.put("majorId", major.getId());
            item.put("majorName", major.getName());
            Optional<EnrollmentPlan> plan = plans.stream()
                    .filter(p -> p.getMajorId().equals(major.getId()))
                    .findFirst();
            item.put("planCount", plan.map(EnrollmentPlan::getPlanCount).orElse(0));
            item.put("actualCount", plan.map(EnrollmentPlan::getActualCount).orElse(0));
            result.add(item);
        }
        return result;
    }

    public List<Map<String, Object>> getProvinceStats() {
        List<Student> students = studentMapper.selectList(null);
        Map<String, Long> provinceCount = students.stream()
                .filter(s -> s.getProvince() != null)
                .collect(Collectors.groupingBy(Student::getProvince, Collectors.counting()));
        List<Map<String, Object>> result = new ArrayList<>();
        provinceCount.forEach((province, count) -> {
            Map<String, Object> item = new HashMap<>();
            item.put("province", province);
            item.put("count", count);
            result.add(item);
        });
        result.sort((a, b) -> Long.compare((Long) b.get("count"), (Long) a.get("count")));
        return result;
    }
}
