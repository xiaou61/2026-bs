package com.vehicleclaim.mapper;

import com.vehicleclaim.entity.VehicleProfile;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface VehicleProfileMapper {
    @Select({
        "<script>",
        "SELECT * FROM budget_category",
        "<where>",
        "<if test='keyword != null and keyword != \"\"'> AND (category_no LIKE CONCAT('%',#{keyword},'%') OR category_name LIKE CONCAT('%',#{keyword},'%') OR usage_scope LIKE CONCAT('%',#{keyword},'%') OR control_mode LIKE CONCAT('%',#{keyword},'%'))</if>",
        "<if test='status != null and status != \"\"'> AND status = #{status}</if>",
        "</where>",
        "ORDER BY id DESC",
        "</script>"
    })
    List<VehicleProfile> selectPage(@Param("keyword") String keyword, @Param("status") String status);

    @Select("SELECT * FROM budget_category WHERE id = #{id}")
    VehicleProfile selectById(Long id);

    @Insert("INSERT INTO budget_category (category_no, category_name, usage_scope, control_mode, manager_name, status, created_time, updated_time) VALUES (#{categoryNo}, #{categoryName}, #{usageScope}, #{controlMode}, #{managerName}, #{status}, NOW(), NOW())")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(VehicleProfile entity);

    @Update("UPDATE budget_category SET category_no = #{categoryNo}, category_name = #{categoryName}, usage_scope = #{usageScope}, control_mode = #{controlMode}, manager_name = #{managerName}, status = #{status}, updated_time = NOW() WHERE id = #{id}")
    int update(VehicleProfile entity);

    @Delete("DELETE FROM budget_category WHERE id = #{id}")
    int deleteById(Long id);

    @Select("SELECT COUNT(*) FROM budget_category")
    long countAll();

    @Update("UPDATE budget_category SET status = #{status}, updated_time = NOW() WHERE id = #{id}")
    int updateStatus(@Param("id") Long id, @Param("status") String status);
}




