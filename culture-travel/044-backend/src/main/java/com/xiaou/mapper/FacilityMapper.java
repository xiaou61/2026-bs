package com.xiaou.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xiaou.entity.Facility;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface FacilityMapper extends BaseMapper<Facility> {
    
    @Select("SELECT f.* FROM facility f " +
            "INNER JOIN homestay_facility hf ON f.id = hf.facility_id " +
            "WHERE hf.homestay_id = #{homestayId} AND f.deleted = 0")
    List<Facility> selectByHomestayId(Long homestayId);
}
