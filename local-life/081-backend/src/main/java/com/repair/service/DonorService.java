package com.repair.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.repair.entity.Donor;
import com.repair.mapper.DonorMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DonorService {

    @Autowired
    private DonorMapper donorMapper;

    public Page<Donor> getList(int pageNum, int pageSize, String donorType) {
        Page<Donor> page = new Page<>(pageNum, pageSize);
        QueryWrapper<Donor> wrapper = new QueryWrapper<>();
        if (donorType != null && !donorType.isEmpty()) {
            wrapper.eq("donor_type", donorType);
        }
        return donorMapper.selectPage(page, wrapper);
    }

    public void add(Donor donor) {
        donorMapper.insert(donor);
    }

    public void update(Donor donor) {
        donorMapper.updateById(donor);
    }

    public void delete(Long id) {
        donorMapper.deleteById(id);
    }
}

