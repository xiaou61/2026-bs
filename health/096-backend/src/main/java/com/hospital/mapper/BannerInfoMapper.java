package com.hospital.mapper;

import com.hospital.entity.BannerInfo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface BannerInfoMapper {
    List<BannerInfo> selectPage(@Param("keyword") String keyword, @Param("status") Integer status);

    List<BannerInfo> selectPublicList();

    void insert(BannerInfo entity);

    void update(BannerInfo entity);

    void deleteById(@Param("id") Long id);
}
