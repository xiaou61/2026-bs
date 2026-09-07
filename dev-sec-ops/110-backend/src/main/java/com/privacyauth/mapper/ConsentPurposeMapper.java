package com.privacyauth.mapper;

import com.privacyauth.entity.ConsentPurpose;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface ConsentPurposeMapper {
    @Select({
        "<script>",
        "SELECT * FROM consent_purpose",
        "<where>",
        "<if test='keyword != null and keyword != \"\"'> AND (purpose_name LIKE CONCAT('%',#{keyword},'%') OR purpose_code LIKE CONCAT('%',#{keyword},'%') OR business_scene LIKE CONCAT('%',#{keyword},'%') OR owner_name LIKE CONCAT('%',#{keyword},'%'))</if>",
        "<if test='status != null and status != \"\"'> AND status = #{status}</if>",
        "</where>",
        "ORDER BY id DESC",
        "</script>"
    })
    List<ConsentPurpose> selectPage(@Param("keyword") String keyword, @Param("status") String status);

    @Select("SELECT * FROM consent_purpose WHERE id = #{id}")
    ConsentPurpose selectById(Long id);

    @Insert("INSERT INTO consent_purpose (purpose_name, purpose_code, business_scene, purpose_text, valid_days, owner_name, status, created_time, updated_time) VALUES (#{purposeName}, #{purposeCode}, #{businessScene}, #{purposeText}, #{validDays}, #{ownerName}, #{status}, NOW(), NOW())")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(ConsentPurpose entity);

    @Update("UPDATE consent_purpose SET purpose_name = #{purposeName}, purpose_code = #{purposeCode}, business_scene = #{businessScene}, purpose_text = #{purposeText}, valid_days = #{validDays}, owner_name = #{ownerName}, status = #{status}, updated_time = NOW() WHERE id = #{id}")
    int update(ConsentPurpose entity);

    @Delete("DELETE FROM consent_purpose WHERE id = #{id}")
    int deleteById(Long id);

    @Select("SELECT COUNT(*) FROM consent_purpose")
    long countAll();

    @Update("UPDATE consent_purpose SET status = #{status}, updated_time = NOW() WHERE id = #{id}")
    int updateStatus(@Param("id") Long id, @Param("status") String status);
}
