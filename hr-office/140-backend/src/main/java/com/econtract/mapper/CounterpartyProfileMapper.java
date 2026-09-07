package com.econtract.mapper;

import com.econtract.entity.CounterpartyProfile;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface CounterpartyProfileMapper {
    @Select({
        "<script>",
        "SELECT * FROM counterparty_profile",
        "<where>",
        "<if test='keyword != null and keyword != \"\"'> AND (counterparty_no LIKE CONCAT('%',#{keyword},'%') OR counterparty_name LIKE CONCAT('%',#{keyword},'%') OR credit_code LIKE CONCAT('%',#{keyword},'%') OR contact_name LIKE CONCAT('%',#{keyword},'%'))</if>",
        "<if test='status != null and status != \"\"'> AND status = #{status}</if>",
        "</where>",
        "ORDER BY id DESC",
        "</script>"
    })
    List<CounterpartyProfile> selectPage(@Param("keyword") String keyword, @Param("status") String status);

    @Select("SELECT * FROM counterparty_profile WHERE id = #{id}")
    CounterpartyProfile selectById(Long id);

    @Insert("INSERT INTO counterparty_profile (counterparty_no, counterparty_name, credit_code, contact_name, contact_phone, status, created_time, updated_time) VALUES (#{counterpartyNo}, #{counterpartyName}, #{creditCode}, #{contactName}, #{contactPhone}, #{status}, NOW(), NOW())")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(CounterpartyProfile entity);

    @Update("UPDATE counterparty_profile SET counterparty_no = #{counterpartyNo}, counterparty_name = #{counterpartyName}, credit_code = #{creditCode}, contact_name = #{contactName}, contact_phone = #{contactPhone}, status = #{status}, updated_time = NOW() WHERE id = #{id}")
    int update(CounterpartyProfile entity);

    @Delete("DELETE FROM counterparty_profile WHERE id = #{id}")
    int deleteById(Long id);

    @Select("SELECT COUNT(*) FROM counterparty_profile")
    long countAll();

    @Update("UPDATE counterparty_profile SET status = #{status}, updated_time = NOW() WHERE id = #{id}")
    int updateStatus(@Param("id") Long id, @Param("status") String status);
}



