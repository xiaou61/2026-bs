package com.zerotrust.mapper;

import com.zerotrust.entity.DeviceCertificate;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface DeviceCertificateMapper {
    @Select({
        "<script>",
        "SELECT * FROM device_certificate",
        "<where>",
        "<if test='keyword != null and keyword != \"\"'> AND (cert_no LIKE CONCAT('%',#{keyword},'%') OR device_name LIKE CONCAT('%',#{keyword},'%') OR issuer_name LIKE CONCAT('%',#{keyword},'%') OR status LIKE CONCAT('%',#{keyword},'%'))</if>",
        "<if test='status != null and status != \"\"'> AND status = #{status}</if>",
        "</where>",
        "ORDER BY id DESC",
        "</script>"
    })
    List<DeviceCertificate> selectPage(@Param("keyword") String keyword, @Param("status") String status);

    @Select("SELECT * FROM device_certificate WHERE id = #{id}")
    DeviceCertificate selectById(Long id);

    @Insert("INSERT INTO device_certificate (cert_no, device_name, issuer_name, issued_time, expire_time, status, created_time, updated_time) VALUES (#{certNo}, #{deviceName}, #{issuerName}, #{issuedTime}, #{expireTime}, #{status}, NOW(), NOW())")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(DeviceCertificate entity);

    @Update("UPDATE device_certificate SET cert_no = #{certNo}, device_name = #{deviceName}, issuer_name = #{issuerName}, issued_time = #{issuedTime}, expire_time = #{expireTime}, status = #{status}, updated_time = NOW() WHERE id = #{id}")
    int update(DeviceCertificate entity);

    @Delete("DELETE FROM device_certificate WHERE id = #{id}")
    int deleteById(Long id);

    @Select("SELECT COUNT(*) FROM device_certificate")
    long countAll();

    @Update("UPDATE device_certificate SET status = #{status}, updated_time = NOW() WHERE id = #{id}")
    int updateStatus(@Param("id") Long id, @Param("status") String status);
}
