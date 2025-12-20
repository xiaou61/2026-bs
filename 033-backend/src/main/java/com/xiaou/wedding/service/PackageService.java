package com.xiaou.wedding.service;

import com.xiaou.wedding.entity.Package;
import java.util.List;

public interface PackageService {
    List<Package> list(String category);

    Package detail(Long id);

    void create(Package pkg);

    void update(Package pkg);

    void delete(Long id);
}
