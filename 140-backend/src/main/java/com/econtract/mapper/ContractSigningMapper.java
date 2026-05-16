package com.econtract.mapper;

import com.econtract.entity.ContractSigning;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface ContractSigningMapper {
    @Select({
        "<script>",
        "SELECT * FROM contract_signing",
        "<where>",
        "<if test='keyword != null and keyword != \"\"'> AND (signing_no LIKE CONCAT('%',#{keyword},'%') OR contract_title LIKE CONCAT('%',#{keyword},'%') OR signatory_name LIKE CONCAT('%',#{keyword},'%') OR signing_status LIKE CONCAT('%',#{keyword},'%'))</if>",
        "<if test='status != null and status != \"\"'> AND status = #{status}</if>",
        "</where>",
        "ORDER BY id DESC",
        "</script>"
    })
    List<ContractSigning> selectPage(@Param("keyword") String keyword, @Param("status") String status);

    @Select("SELECT * FROM contract_signing WHERE id = #{id}")
    ContractSigning selectById(Long id);

    @Insert("INSERT INTO contract_signing (signing_no, contract_title, signatory_name, sign_time, signing_status, status, created_time, updated_time) VALUES (#{signingNo}, #{contractTitle}, #{signatoryName}, #{signTime}, #{signingStatus}, #{status}, NOW(), NOW())")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(ContractSigning entity);

    @Update("UPDATE contract_signing SET signing_no = #{signingNo}, contract_title = #{contractTitle}, signatory_name = #{signatoryName}, sign_time = #{signTime}, signing_status = #{signingStatus}, status = #{status}, updated_time = NOW() WHERE id = #{id}")
    int update(ContractSigning entity);

    @Delete("DELETE FROM contract_signing WHERE id = #{id}")
    int deleteById(Long id);

    @Select("SELECT COUNT(*) FROM contract_signing")
    long countAll();

    @Update("UPDATE contract_signing SET status = #{status}, updated_time = NOW() WHERE id = #{id}")
    int updateStatus(@Param("id") Long id, @Param("status") String status);
}



