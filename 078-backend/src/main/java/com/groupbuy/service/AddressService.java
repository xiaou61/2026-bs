package com.groupbuy.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.groupbuy.entity.Address;
import com.groupbuy.mapper.AddressMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class AddressService {

    @Autowired
    private AddressMapper addressMapper;

    public List<Address> list(Long userId) {
        QueryWrapper<Address> wrapper = new QueryWrapper<>();
        wrapper.eq("user_id", userId).orderByDesc("is_default", "create_time");
        return addressMapper.selectList(wrapper);
    }

    @Transactional
    public void add(Long userId, Address address) {
        address.setUserId(userId);
        if (address.getIsDefault() == 1) {
            clearDefault(userId);
        }
        addressMapper.insert(address);
    }

    @Transactional
    public void update(Address address) {
        if (address.getIsDefault() == 1) {
            Address old = addressMapper.selectById(address.getId());
            clearDefault(old.getUserId());
        }
        addressMapper.updateById(address);
    }

    public void delete(Long id) {
        addressMapper.deleteById(id);
    }

    @Transactional
    public void setDefault(Long userId, Long id) {
        clearDefault(userId);
        Address address = new Address();
        address.setId(id);
        address.setIsDefault(1);
        addressMapper.updateById(address);
    }

    private void clearDefault(Long userId) {
        QueryWrapper<Address> wrapper = new QueryWrapper<>();
        wrapper.eq("user_id", userId).eq("is_default", 1);
        addressMapper.selectList(wrapper).forEach(a -> {
            a.setIsDefault(0);
            addressMapper.updateById(a);
        });
    }
}
