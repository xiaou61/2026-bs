package com.hospitalcare.mapper;

import com.hospitalcare.entity.FamilyCommunication;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface FamilyCommunicationMapper {
    @Select({"<script>", "SELECT * FROM family_communication", "<where>", "<if test='keyword != null and keyword != \"\"'> AND (record_no LIKE CONCAT('%',#{keyword},'%') OR record_name LIKE CONCAT('%',#{keyword},'%') OR category LIKE CONCAT('%',#{keyword},'%') OR owner_name LIKE CONCAT('%',#{keyword},'%'))</if>", "<if test='status != null and status != \"\"'> AND status = #{status}</if>", "</where>", "ORDER BY id DESC", "</script>"})
    List<FamilyCommunication> selectPage(@Param("keyword") String keyword, @Param("status") String status);

    @Insert("INSERT INTO family_communication (record_no,record_name,category,owner_name,plan_time,status,remark,created_time,updated_time) VALUES (#{recordNo},#{recordName},#{category},#{ownerName},#{planTime},#{status},#{remark},NOW(),NOW())")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(FamilyCommunication entity);

    @Update("UPDATE family_communication SET record_no=#{recordNo},record_name=#{recordName},category=#{category},owner_name=#{ownerName},plan_time=#{planTime},status=#{status},remark=#{remark},updated_time=NOW() WHERE id=#{id}")
    int update(FamilyCommunication entity);

    @Delete("DELETE FROM family_communication WHERE id=#{id}")
    int deleteById(Long id);

    @Select("SELECT COUNT(*) FROM family_communication")
    long countAll();

    @Update("UPDATE family_communication SET status=#{status},updated_time=NOW() WHERE id=#{id}")
    int updateStatus(@Param("id") Long id, @Param("status") String status);
}
