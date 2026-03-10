package com.teachres.mapper;

import com.teachres.entity.MaterialAudit;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface MaterialAuditMapper {
    int insert(MaterialAudit materialAudit);
    List<MaterialAudit> selectList(@Param("materialId") Long materialId);
}
