package com.legalcase.mapper;

import com.legalcase.entity.ClientProfile;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface ClientProfileMapper {
    @Select({"<script>",
            "SELECT * FROM client_profile",
            "<where>",
            "<if test=\"keyword != null and keyword != \'\'\"> AND (real_name LIKE CONCAT('%',#{keyword},'%') OR phone LIKE CONCAT('%',#{keyword},'%') OR case_preference LIKE CONCAT('%',#{keyword},'%')) </if>",
            "<if test=\"status != null\"> AND status = #{status} </if>",
            "</where>",
            "ORDER BY id DESC",
            "</script>"})
    List<ClientProfile> selectPage(@Param("keyword") String keyword, @Param("status") Integer status);

    @Select("SELECT * FROM client_profile WHERE id=#{id}")
    ClientProfile selectById(Long id);

    @Insert("INSERT INTO client_profile(user_id, real_name, id_no, phone, address, case_preference, status, create_time, update_time) VALUES(#{userId}, #{realName}, #{idNo}, #{phone}, #{address}, #{casePreference}, #{status}, #{createTime}, #{updateTime})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(ClientProfile entity);

    @Update("UPDATE client_profile SET user_id=#{userId}, real_name=#{realName}, id_no=#{idNo}, phone=#{phone}, address=#{address}, case_preference=#{casePreference}, status=#{status}, create_time=#{createTime}, update_time=#{updateTime} WHERE id=#{id}")
    int updateById(ClientProfile entity);

    @Delete("DELETE FROM client_profile WHERE id=#{id}")
    int deleteById(Long id);

    @Select("SELECT COUNT(*) FROM client_profile")
    long countAll();
}
