package com.hrm.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.hrm.entity.Salary;
import com.hrm.mapper.SalaryMapper;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.Date;

@Service
public class SalaryService {
    @Resource
    private SalaryMapper salaryMapper;

    public Salary getById(Long id) {
        return salaryMapper.selectById(id);
    }

    public PageInfo<Salary> getList(Long employeeId, String employeeName, String yearMonth, String status,
                                     Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        return new PageInfo<>(salaryMapper.selectList(employeeId, employeeName, yearMonth, status));
    }

    public void add(Salary salary) {
        calculateActualSalary(salary);
        salary.setStatus("pending");
        salaryMapper.insert(salary);
    }

    public void update(Salary salary) {
        calculateActualSalary(salary);
        salaryMapper.update(salary);
    }

    public void delete(Long id) {
        salaryMapper.deleteById(id);
    }

    public void pay(Long id) {
        Salary salary = new Salary();
        salary.setId(id);
        salary.setStatus("paid");
        salary.setPayDate(new Date());
        salaryMapper.update(salary);
    }

    private void calculateActualSalary(Salary salary) {
        BigDecimal base = salary.getBaseSalary() != null ? salary.getBaseSalary() : BigDecimal.ZERO;
        BigDecimal perf = salary.getPerformance() != null ? salary.getPerformance() : BigDecimal.ZERO;
        BigDecimal allow = salary.getAllowance() != null ? salary.getAllowance() : BigDecimal.ZERO;
        BigDecimal deduct = salary.getDeduction() != null ? salary.getDeduction() : BigDecimal.ZERO;
        salary.setActualSalary(base.add(perf).add(allow).subtract(deduct));
    }
}
