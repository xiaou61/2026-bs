package com.cloudcost.mapper;

import com.cloudcost.entity.CloudAccount;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface CloudAccountMapper {
    @Select({
        "<script>",
        "SELECT * FROM cloud_account",
        "<where>",
        "<if test='keyword != null and keyword != \"\"'> AND (account_name LIKE CONCAT('%',#{keyword},'%') OR account_code LIKE CONCAT('%',#{keyword},'%') OR provider LIKE CONCAT('%',#{keyword},'%') OR owner_name LIKE CONCAT('%',#{keyword},'%'))</if>",
        "<if test='status != null and status != \"\"'> AND status = #{status}</if>",
        "</where>",
        "ORDER BY id DESC",
        "</script>"
    })
    List<CloudAccount> selectPage(@Param("keyword") String keyword, @Param("status") String status);

    @Select("SELECT * FROM cloud_account WHERE id = #{id}")
    CloudAccount selectById(Long id);

    @Insert("INSERT INTO cloud_account (account_name, account_code, provider, owner_name, access_mode, monthly_budget, status, created_time, updated_time) VALUES (#{accountName}, #{accountCode}, #{provider}, #{ownerName}, #{accessMode}, #{monthlyBudget}, #{status}, NOW(), NOW())")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(CloudAccount entity);

    @Update("UPDATE cloud_account SET account_name = #{accountName}, account_code = #{accountCode}, provider = #{provider}, owner_name = #{ownerName}, access_mode = #{accessMode}, monthly_budget = #{monthlyBudget}, status = #{status}, updated_time = NOW() WHERE id = #{id}")
    int update(CloudAccount entity);

    @Delete("DELETE FROM cloud_account WHERE id = #{id}")
    int deleteById(Long id);

    @Select("SELECT COUNT(*) FROM cloud_account")
    long countAll();

    @Update("UPDATE cloud_account SET status = #{status}, updated_time = NOW() WHERE id = #{id}")
    int updateStatus(@Param("id") Long id, @Param("status") String status);
}
