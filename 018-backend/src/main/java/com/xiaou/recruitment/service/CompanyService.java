package com.xiaou.recruitment.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xiaou.recruitment.entity.Company;
import com.xiaou.recruitment.mapper.CompanyMapper;
import org.springframework.stereotype.Service;

@Service
public class CompanyService extends ServiceImpl<CompanyMapper, Company> {

    public boolean registerCompany(Company company) {
        company.setStatus(0);
        return save(company);
    }

    public Company getCompanyById(Long id) {
        return getById(id);
    }

    public boolean updateCompany(Company company) {
        return updateById(company);
    }

    public IPage<Company> getCompanyList(Integer page, Integer size, Integer status) {
        Page<Company> pageParam = new Page<>(page, size);
        LambdaQueryWrapper<Company> wrapper = new LambdaQueryWrapper<>();
        if (status != null) {
            wrapper.eq(Company::getStatus, status);
        }
        wrapper.orderByDesc(Company::getCreatedAt);
        return page(pageParam, wrapper);
    }
}
