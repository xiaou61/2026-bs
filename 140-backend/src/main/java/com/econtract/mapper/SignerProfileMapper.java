package com.econtract.mapper;

import com.econtract.entity.SignerProfile;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface SignerProfileMapper {
    @Select({
        "<script>",
        "SELECT * FROM signer_profile",
        "<where>",
        "<if test='keyword != null and keyword != \"\"'> AND (signer_no LIKE CONCAT('%',#{keyword},'%') OR signer_name LIKE CONCAT('%',#{keyword},'%') OR signer_type LIKE CONCAT('%',#{keyword},'%') OR certificate_no LIKE CONCAT('%',#{keyword},'%'))</if>",
        "<if test='status != null and status != \"\"'> AND status = #{status}</if>",
        "</where>",
        "ORDER BY id DESC",
        "</script>"
    })
    List<SignerProfile> selectPage(@Param("keyword") String keyword, @Param("status") String status);

    @Select("SELECT * FROM signer_profile WHERE id = #{id}")
    SignerProfile selectById(Long id);

    @Insert("INSERT INTO signer_profile (signer_no, signer_name, signer_type, certificate_no, authorization_status, status, created_time, updated_time) VALUES (#{signerNo}, #{signerName}, #{signerType}, #{certificateNo}, #{authorizationStatus}, #{status}, NOW(), NOW())")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(SignerProfile entity);

    @Update("UPDATE signer_profile SET signer_no = #{signerNo}, signer_name = #{signerName}, signer_type = #{signerType}, certificate_no = #{certificateNo}, authorization_status = #{authorizationStatus}, status = #{status}, updated_time = NOW() WHERE id = #{id}")
    int update(SignerProfile entity);

    @Delete("DELETE FROM signer_profile WHERE id = #{id}")
    int deleteById(Long id);

    @Select("SELECT COUNT(*) FROM signer_profile")
    long countAll();

    @Update("UPDATE signer_profile SET status = #{status}, updated_time = NOW() WHERE id = #{id}")
    int updateStatus(@Param("id") Long id, @Param("status") String status);
}



