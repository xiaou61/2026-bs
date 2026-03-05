package com.alumni.service;

import com.alumni.entity.AlumniCompany;
import com.alumni.entity.User;
import com.alumni.mapper.AlumniCompanyMapper;
import com.alumni.mapper.UserMapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class CompanyService {

    @Autowired
    private AlumniCompanyMapper alumniCompanyMapper;

    @Autowired
    private UserMapper userMapper;

    public Page<AlumniCompany> list(Integer pageNum, Integer pageSize, String name, String industry, Integer status) {
        Page<AlumniCompany> page = new Page<>(pageNum, pageSize);
        LambdaQueryWrapper<AlumniCompany> wrapper = new LambdaQueryWrapper<>();
        if (StringUtils.hasText(name)) {
            wrapper.like(AlumniCompany::getName, name);
        }
        if (StringUtils.hasText(industry)) {
            wrapper.eq(AlumniCompany::getIndustry, industry);
        }
        if (status != null) {
            wrapper.eq(AlumniCompany::getStatus, status);
        }
        wrapper.orderByDesc(AlumniCompany::getCreateTime);
        Page<AlumniCompany> result = alumniCompanyMapper.selectPage(page, wrapper);
        List<User> users = userMapper.selectList(null);
        Map<Long, String> userMap = users.stream().collect(Collectors.toMap(User::getId, User::getName));
        result.getRecords().forEach(c -> c.setUserName(userMap.get(c.getUserId())));
        return result;
    }

    public AlumniCompany getById(Long id) {
        AlumniCompany company = alumniCompanyMapper.selectById(id);
        if (company != null) {
            User user = userMapper.selectById(company.getUserId());
            if (user != null) company.setUserName(user.getName());
        }
        return company;
    }

    public void add(AlumniCompany company) {
        company.setStatus(0);
        alumniCompanyMapper.insert(company);
    }

    public void update(AlumniCompany company) {
        alumniCompanyMapper.updateById(company);
    }

    public void delete(Long id) {
        alumniCompanyMapper.deleteById(id);
    }

    public void audit(Long id, Integer status) {
        AlumniCompany company = new AlumniCompany();
        company.setId(id);
        company.setStatus(status);
        alumniCompanyMapper.updateById(company);
    }
}
