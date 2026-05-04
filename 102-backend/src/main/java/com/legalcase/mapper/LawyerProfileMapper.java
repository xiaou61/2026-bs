package com.legalcase.mapper;

import com.legalcase.entity.LawyerProfile;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface LawyerProfileMapper {
    @Select({"<script>",
            "SELECT * FROM lawyer_profile",
            "<where>",
            "<if test=\"keyword != null and keyword != \'\'\"> AND (real_name LIKE CONCAT('%',#{keyword},'%') OR license_no LIKE CONCAT('%',#{keyword},'%') OR speciality LIKE CONCAT('%',#{keyword},'%')) </if>",
            "<if test=\"serviceStatus != null\"> AND service_status = #{serviceStatus} </if>",
            "</where>",
            "ORDER BY id DESC",
            "</script>"})
    List<LawyerProfile> selectPage(@Param("keyword") String keyword, @Param("serviceStatus") Integer serviceStatus);

    @Select("SELECT * FROM lawyer_profile WHERE id=#{id}")
    LawyerProfile selectById(Long id);

    @Insert("INSERT INTO lawyer_profile(user_id, real_name, license_no, speciality, experience_years, service_status, create_time, update_time) VALUES(#{userId}, #{realName}, #{licenseNo}, #{speciality}, #{experienceYears}, #{serviceStatus}, #{createTime}, #{updateTime})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(LawyerProfile entity);

    @Update("UPDATE lawyer_profile SET user_id=#{userId}, real_name=#{realName}, license_no=#{licenseNo}, speciality=#{speciality}, experience_years=#{experienceYears}, service_status=#{serviceStatus}, create_time=#{createTime}, update_time=#{updateTime} WHERE id=#{id}")
    int updateById(LawyerProfile entity);

    @Delete("DELETE FROM lawyer_profile WHERE id=#{id}")
    int deleteById(Long id);

    @Select("SELECT COUNT(*) FROM lawyer_profile")
    long countAll();
}
