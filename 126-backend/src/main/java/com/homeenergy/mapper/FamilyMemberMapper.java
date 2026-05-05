package com.homeenergy.mapper;

import com.homeenergy.entity.FamilyMember;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface FamilyMemberMapper {
    @Select({
        "<script>",
        "SELECT * FROM family_member",
        "<where>",
        "<if test='keyword != null and keyword != \"\"'> AND (member_no LIKE CONCAT('%',#{keyword},'%') OR home_no LIKE CONCAT('%',#{keyword},'%') OR member_name LIKE CONCAT('%',#{keyword},'%') OR relation_type LIKE CONCAT('%',#{keyword},'%'))</if>",
        "<if test='status != null and status != \"\"'> AND status = #{status}</if>",
        "</where>",
        "ORDER BY id DESC",
        "</script>"
    })
    List<FamilyMember> selectPage(@Param("keyword") String keyword, @Param("status") String status);

    @Select("SELECT * FROM family_member WHERE id = #{id}")
    FamilyMember selectById(Long id);

    @Insert("INSERT INTO family_member (member_no, home_no, member_name, relation_type, phone, usage_preference, status, created_time, updated_time) VALUES (#{memberNo}, #{homeNo}, #{memberName}, #{relationType}, #{phone}, #{usagePreference}, #{status}, NOW(), NOW())")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(FamilyMember entity);

    @Update("UPDATE family_member SET member_no = #{memberNo}, home_no = #{homeNo}, member_name = #{memberName}, relation_type = #{relationType}, phone = #{phone}, usage_preference = #{usagePreference}, status = #{status}, updated_time = NOW() WHERE id = #{id}")
    int update(FamilyMember entity);

    @Delete("DELETE FROM family_member WHERE id = #{id}")
    int deleteById(Long id);

    @Select("SELECT COUNT(*) FROM family_member")
    long countAll();

    @Update("UPDATE family_member SET status = #{status}, updated_time = NOW() WHERE id = #{id}")
    int updateStatus(@Param("id") Long id, @Param("status") String status);
}
