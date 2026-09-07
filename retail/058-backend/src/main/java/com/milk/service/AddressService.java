package com.milk.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.milk.entity.Address;
import com.milk.mapper.AddressMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class AddressService {

    @Resource
    private AddressMapper addressMapper;

    public List<Address> listByUserId(Long userId) {
        return addressMapper.selectList(new QueryWrapper<Address>().eq("user_id", userId).orderByDesc("is_default"));
    }

    public void save(Address address) {
        if (address.getId() == null) {
            addressMapper.insert(address);
        } else {
            addressMapper.updateById(address);
        }
    }

    public void deleteById(Long id) {
        addressMapper.deleteById(id);
    }

    public void setDefault(Long id, Long userId) {
        addressMapper.update(null, new UpdateWrapper<Address>().eq("user_id", userId).set("is_default", 0));
        addressMapper.update(null, new UpdateWrapper<Address>().eq("id", id).set("is_default", 1));
    }
}
