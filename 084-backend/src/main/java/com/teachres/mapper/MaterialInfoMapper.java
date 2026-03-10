package com.teachres.mapper;

import com.teachres.entity.MaterialInfo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface MaterialInfoMapper {
    MaterialInfo selectById(Long id);
    List<MaterialInfo> selectList(@Param("title") String title,
                                  @Param("categoryId") Long categoryId,
                                  @Param("auditStatus") Integer auditStatus,
                                  @Param("publishStatus") Integer publishStatus,
                                  @Param("uploaderId") Long uploaderId);
    List<MaterialInfo> selectPublicList(@Param("title") String title, @Param("categoryId") Long categoryId);
    int insert(MaterialInfo materialInfo);
    int update(MaterialInfo materialInfo);
    int deleteById(Long id);
    int incrementDownloadCount(Long id);
    int incrementFavoriteCount(Long id);
    int decrementFavoriteCount(Long id);
    long countAll();
    long countByAuditStatus(Integer auditStatus);
}
