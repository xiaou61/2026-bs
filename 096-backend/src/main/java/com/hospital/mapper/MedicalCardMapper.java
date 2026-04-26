package com.hospital.mapper;

import com.hospital.entity.MedicalCard;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface MedicalCardMapper {
    List<MedicalCard> selectByUserId(@Param("userId") Long userId);

    MedicalCard selectById(@Param("id") Long id);

    MedicalCard selectDefaultByUserId(@Param("userId") Long userId);

    void insert(MedicalCard entity);

    void update(MedicalCard entity);

    void clearDefault(@Param("userId") Long userId);

    void deleteById(@Param("id") Long id, @Param("userId") Long userId);

    long countByUserId(@Param("userId") Long userId);
}
