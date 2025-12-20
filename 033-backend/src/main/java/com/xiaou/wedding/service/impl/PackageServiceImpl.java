package com.xiaou.wedding.service.impl;

import com.xiaou.wedding.entity.Package;
import com.xiaou.wedding.exception.BusinessException;
import com.xiaou.wedding.mapper.PackageMapper;
import com.xiaou.wedding.service.PackageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PackageServiceImpl implements PackageService {

    @Autowired
    private PackageMapper packageMapper;

    @Override
    public List<Package> list(String category) {
        return packageMapper.findList(category);
    }

    @Override
    public Package detail(Long id) {
        Package pkg = packageMapper.findById(id);
        if (pkg == null) {
            throw new BusinessException("Package not found");
        }
        return pkg;
    }

    @Override
    public void create(Package pkg) {
        pkg.setStatus(pkg.getStatus() == null ? 1 : pkg.getStatus());
        pkg.setSalesCount(pkg.getSalesCount() == null ? 0 : pkg.getSalesCount());
        packageMapper.insert(pkg);
    }

    @Override
    public void update(Package pkg) {
        if (packageMapper.findById(pkg.getId()) == null) {
            throw new BusinessException("Package not found");
        }
        packageMapper.update(pkg);
    }

    @Override
    public void delete(Long id) {
        if (packageMapper.findById(id) == null) {
            throw new BusinessException("Package not found");
        }
        packageMapper.logicDelete(id);
    }
}
