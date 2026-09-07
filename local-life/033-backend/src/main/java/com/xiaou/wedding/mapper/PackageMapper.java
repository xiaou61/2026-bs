package com.xiaou.wedding.mapper;

import com.xiaou.wedding.entity.Package;
import org.apache.ibatis.annotations.Param;
import java.util.List;

public interface PackageMapper {
    Package findById(@Param("id") Long id);

    List<Package> findList(@Param("category") String category);

    int insert(Package pkg);

    int update(Package pkg);

    int logicDelete(@Param("id") Long id);
}
