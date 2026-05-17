package com.heritageworkshop.mapper;

import com.heritageworkshop.entity.SalesSettlement;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface SalesSettlementMapper {
    @Select({"<script>", "SELECT * FROM sales_settlement", "<where>", "<if test='keyword != null and keyword != \"\"'> AND (record_no LIKE CONCAT('%',#{keyword},'%') OR record_name LIKE CONCAT('%',#{keyword},'%') OR category LIKE CONCAT('%',#{keyword},'%') OR owner_name LIKE CONCAT('%',#{keyword},'%'))</if>", "<if test='status != null and status != \"\"'> AND status = #{status}</if>", "</where>", "ORDER BY id DESC", "</script>"})
    List<SalesSettlement> selectPage(@Param("keyword") String keyword, @Param("status") String status);

    @Insert("INSERT INTO sales_settlement (record_no,record_name,category,owner_name,plan_time,status,remark,created_time,updated_time) VALUES (#{recordNo},#{recordName},#{category},#{ownerName},#{planTime},#{status},#{remark},NOW(),NOW())")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(SalesSettlement entity);

    @Update("UPDATE sales_settlement SET record_no=#{recordNo},record_name=#{recordName},category=#{category},owner_name=#{ownerName},plan_time=#{planTime},status=#{status},remark=#{remark},updated_time=NOW() WHERE id=#{id}")
    int update(SalesSettlement entity);

    @Delete("DELETE FROM sales_settlement WHERE id=#{id}")
    int deleteById(Long id);

    @Select("SELECT COUNT(*) FROM sales_settlement")
    long countAll();

    @Update("UPDATE sales_settlement SET status=#{status},updated_time=NOW() WHERE id=#{id}")
    int updateStatus(@Param("id") Long id, @Param("status") String status);
}
