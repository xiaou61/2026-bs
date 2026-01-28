package com.oa.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.oa.entity.Salary;
import com.oa.entity.User;
import com.oa.mapper.SalaryMapper;
import com.oa.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class SalaryService {
    private final SalaryMapper salaryMapper;
    private final UserMapper userMapper;

    public Page<Salary> getList(Integer pageNum, Integer pageSize, String yearMonth, String keyword) {
        Page<Salary> page = new Page<>(pageNum, pageSize);
        LambdaQueryWrapper<Salary> wrapper = new LambdaQueryWrapper<>();
        if (StringUtils.hasText(yearMonth)) {
            wrapper.eq(Salary::getYearMonth, yearMonth);
        }
        if (StringUtils.hasText(keyword)) {
            wrapper.inSql(Salary::getUserId, "SELECT id FROM user WHERE real_name LIKE '%" + keyword + "%'");
        }
        wrapper.orderByDesc(Salary::getYearMonth);
        Page<Salary> result = salaryMapper.selectPage(page, wrapper);
        result.getRecords().forEach(s -> {
            User user = userMapper.selectById(s.getUserId());
            if (user != null) s.setRealName(user.getRealName());
        });
        return result;
    }

    public Page<Salary> getMyList(Long userId, Integer pageNum, Integer pageSize, String yearMonth) {
        Page<Salary> page = new Page<>(pageNum, pageSize);
        LambdaQueryWrapper<Salary> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Salary::getUserId, userId);
        if (StringUtils.hasText(yearMonth)) {
            wrapper.eq(Salary::getYearMonth, yearMonth);
        }
        wrapper.orderByDesc(Salary::getYearMonth);
        return salaryMapper.selectPage(page, wrapper);
    }

    public void add(Salary salary) {
        calculateActual(salary);
        salaryMapper.insert(salary);
    }

    public void update(Salary salary) {
        calculateActual(salary);
        salaryMapper.updateById(salary);
    }

    private void calculateActual(Salary salary) {
        BigDecimal basic = salary.getBasicSalary() != null ? salary.getBasicSalary() : BigDecimal.ZERO;
        BigDecimal bonus = salary.getBonus() != null ? salary.getBonus() : BigDecimal.ZERO;
        BigDecimal allowance = salary.getAllowance() != null ? salary.getAllowance() : BigDecimal.ZERO;
        BigDecimal deduction = salary.getDeduction() != null ? salary.getDeduction() : BigDecimal.ZERO;
        BigDecimal social = salary.getSocialSecurity() != null ? salary.getSocialSecurity() : BigDecimal.ZERO;
        salary.setActualSalary(basic.add(bonus).add(allowance).subtract(deduction).subtract(social));
    }

    public void delete(Long id) {
        salaryMapper.deleteById(id);
    }
}
