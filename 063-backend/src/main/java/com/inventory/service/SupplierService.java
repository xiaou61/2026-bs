package com.inventory.service;

import cn.hutool.core.util.IdUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.inventory.common.BusinessException;
import com.inventory.common.PageResult;
import com.inventory.entity.Supplier;
import com.inventory.mapper.SupplierMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class SupplierService {

    @Resource
    private SupplierMapper supplierMapper;

    public PageResult<Supplier> page(Integer pageNum, Integer pageSize, String name, String contactPerson, Integer status) {
        PageHelper.startPage(pageNum, pageSize);
        List<Supplier> list = supplierMapper.selectPageList(name, contactPerson, status);
        PageInfo<Supplier> pageInfo = new PageInfo<>(list);
        PageResult<Supplier> result = new PageResult<>();
        result.setTotal(pageInfo.getTotal());
        result.setRecords(pageInfo.getList());
        return result;
    }

    public List<Supplier> list() {
        return supplierMapper.selectEnabledList();
    }

    public void save(Supplier supplier) {
        if (supplier == null || supplier.getName() == null || supplier.getName().trim().isEmpty()) {
            throw new BusinessException("供应商名称不能为空");
        }
        supplier.setName(supplier.getName().trim());
        if (supplier.getContactPerson() != null) {
            supplier.setContactPerson(supplier.getContactPerson().trim());
        }
        if (supplier.getPhone() != null) {
            supplier.setPhone(supplier.getPhone().trim());
        }
        if (supplier.getAddress() != null) {
            supplier.setAddress(supplier.getAddress().trim());
        }
        if (supplier.getStatus() == null) {
            supplier.setStatus(1);
        }
        if (supplier.getId() == null) {
            supplier.setSupplierNo("SUP" + IdUtil.getSnowflakeNextIdStr());
            supplierMapper.insert(supplier);
        } else {
            Supplier db = supplierMapper.selectById(supplier.getId());
            if (db == null) {
                throw new BusinessException("供应商不存在");
            }
            if (supplier.getSupplierNo() == null || supplier.getSupplierNo().trim().isEmpty()) {
                supplier.setSupplierNo(db.getSupplierNo());
            } else {
                supplier.setSupplierNo(supplier.getSupplierNo().trim());
            }
            supplierMapper.update(supplier);
        }
    }

    public void deleteById(Long id) {
        supplierMapper.deleteById(id);
    }

    public Supplier mustGetById(Long id) {
        Supplier supplier = supplierMapper.selectById(id);
        if (supplier == null) {
            throw new BusinessException("供应商不存在");
        }
        return supplier;
    }

    public Long countAll() {
        Long count = supplierMapper.countAll();
        return count == null ? 0L : count;
    }
}
