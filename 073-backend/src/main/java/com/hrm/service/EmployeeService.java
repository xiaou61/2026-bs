package com.hrm.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.hrm.entity.Employee;
import com.hrm.mapper.EmployeeMapper;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class EmployeeService {
    @Resource
    private EmployeeMapper employeeMapper;

    public Employee getById(Long id) {
        return employeeMapper.selectById(id);
    }

    public PageInfo<Employee> getList(String name, String empNo, Long departmentId, String status,
                                       Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        return new PageInfo<>(employeeMapper.selectList(name, empNo, departmentId, status));
    }

    public List<Employee> getAll() {
        return employeeMapper.selectAll();
    }

    public void add(Employee employee) {
        String maxEmpNo = employeeMapper.selectMaxEmpNo();
        if (maxEmpNo == null) {
            employee.setEmpNo("EMP001");
        } else {
            int num = Integer.parseInt(maxEmpNo.substring(3)) + 1;
            employee.setEmpNo(String.format("EMP%03d", num));
        }
        if (employee.getStatus() == null) {
            employee.setStatus("trial");
        }
        employeeMapper.insert(employee);
    }

    public void update(Employee employee) {
        employeeMapper.update(employee);
    }

    public void delete(Long id) {
        employeeMapper.deleteById(id);
    }

    public Map<String, Object> getStatistics() {
        Map<String, Object> stats = new HashMap<>();
        stats.put("total", employeeMapper.countAll());
        stats.put("trial", employeeMapper.countByStatus("trial"));
        stats.put("regular", employeeMapper.countByStatus("regular"));
        stats.put("resigned", employeeMapper.countByStatus("resigned"));
        return stats;
    }
}
