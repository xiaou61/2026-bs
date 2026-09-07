package com.accessibletravel.mapper;

import com.accessibletravel.entity.FacilityPoint;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface FacilityPointMapper {
    @Select({
        "<script>",
        "SELECT * FROM facility_point",
        "<where>",
        "<if test='keyword != null and keyword != \"\"'> AND (point_no LIKE CONCAT('%',#{keyword},'%') OR point_name LIKE CONCAT('%',#{keyword},'%') OR facility_type LIKE CONCAT('%',#{keyword},'%') OR address_detail LIKE CONCAT('%',#{keyword},'%'))</if>",
        "<if test='status != null and status != \"\"'> AND status = #{status}</if>",
        "</where>",
        "ORDER BY id DESC",
        "</script>"
    })
    List<FacilityPoint> selectPage(@Param("keyword") String keyword, @Param("status") String status);

    @Select("SELECT * FROM facility_point WHERE id = #{id}")
    FacilityPoint selectById(Long id);

    @Insert("INSERT INTO facility_point (point_no, point_name, facility_type, address_detail, open_status, status, created_time, updated_time) VALUES (#{pointNo}, #{pointName}, #{facilityType}, #{addressDetail}, #{openStatus}, #{status}, NOW(), NOW())")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(FacilityPoint entity);

    @Update("UPDATE facility_point SET point_no = #{pointNo}, point_name = #{pointName}, facility_type = #{facilityType}, address_detail = #{addressDetail}, open_status = #{openStatus}, status = #{status}, updated_time = NOW() WHERE id = #{id}")
    int update(FacilityPoint entity);

    @Delete("DELETE FROM facility_point WHERE id = #{id}")
    int deleteById(Long id);

    @Select("SELECT COUNT(*) FROM facility_point")
    long countAll();

    @Update("UPDATE facility_point SET status = #{status}, updated_time = NOW() WHERE id = #{id}")
    int updateStatus(@Param("id") Long id, @Param("status") String status);
}

