package com.canteenhygiene.mapper;

import com.canteenhygiene.entity.HygieneInspection;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface HygieneInspectionMapper {
    @Select({"<script>", "SELECT * FROM hygiene_inspection", "<where>", "<if test='keyword != null and keyword != \"\"'> AND (record_no LIKE CONCAT('%',#{keyword},'%') OR record_name LIKE CONCAT('%',#{keyword},'%') OR category LIKE CONCAT('%',#{keyword},'%') OR owner_name LIKE CONCAT('%',#{keyword},'%'))</if>", "<if test='status != null and status != \"\"'> AND status = #{status}</if>", "</where>", "ORDER BY id DESC", "</script>"})
    List<HygieneInspection> selectPage(@Param("keyword") String keyword, @Param("status") String status);

    @Insert("INSERT INTO hygiene_inspection (record_no,record_name,category,owner_name,plan_time,status,remark,created_time,updated_time) VALUES (#{recordNo},#{recordName},#{category},#{ownerName},#{planTime},#{status},#{remark},NOW(),NOW())")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(HygieneInspection entity);

    @Update("UPDATE hygiene_inspection SET record_no=#{recordNo},record_name=#{recordName},category=#{category},owner_name=#{ownerName},plan_time=#{planTime},status=#{status},remark=#{remark},updated_time=NOW() WHERE id=#{id}")
    int update(HygieneInspection entity);

    @Delete("DELETE FROM hygiene_inspection WHERE id=#{id}")
    int deleteById(Long id);

    @Select("SELECT COUNT(*) FROM hygiene_inspection")
    long countAll();

    @Update("UPDATE hygiene_inspection SET status=#{status},updated_time=NOW() WHERE id=#{id}")
    int updateStatus(@Param("id") Long id, @Param("status") String status);
}
