package com.groupbuy.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.groupbuy.common.BusinessException;
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
        if (Integer.valueOf(1).equals(address.getIsDefault())) {
            clearDefault(userId);
        }
        addressMapper.insert(address);
    }

    @Transactional
    public void update(Long userId, Address address) {
        Address old = requireOwnedAddress(userId, address.getId());
        address.setUserId(userId);
        if (Integer.valueOf(1).equals(address.getIsDefault())) {
            clearDefault(old.getUserId());
        }
        addressMapper.updateById(address);
    }

    public void delete(Long userId, Long id) {
        requireOwnedAddress(userId, id);
        addressMapper.deleteById(id);
    }

    @Transactional
    public void setDefault(Long userId, Long id) {
        requireOwnedAddress(userId, id);
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

    private Address requireOwnedAddress(Long userId, Long id) {
        Address address = addressMapper.selectById(id);
        if (address == null) {
            throw new BusinessException(404, "收货地址不存在");
        }
        if (!userId.equals(address.getUserId())) {
            throw new BusinessException(403, "无权操作该收货地址");
        }
        return address;
    }
}
