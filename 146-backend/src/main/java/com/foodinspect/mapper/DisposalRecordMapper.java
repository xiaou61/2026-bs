package com.foodinspect.mapper;

import com.foodinspect.entity.DisposalRecord;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface DisposalRecordMapper {
    @Select({
        "<script>",
        "SELECT * FROM disposal_record",
        "<where>",
        "<if test='keyword != null and keyword != \"\"'> AND (disposal_no LIKE CONCAT('%',#{keyword},'%') OR food_name LIKE CONCAT('%',#{keyword},'%') OR disposal_method LIKE CONCAT('%',#{keyword},'%') OR responsible_unit LIKE CONCAT('%',#{keyword},'%'))</if>",
        "<if test='status != null and status != \"\"'> AND status = #{status}</if>",
        "</where>",
        "ORDER BY id DESC",
        "</script>"
    })
    List<DisposalRecord> selectPage(@Param("keyword") String keyword, @Param("status") String status);

    @Select("SELECT * FROM disposal_record WHERE id = #{id}")
    DisposalRecord selectById(Long id);

    @Insert("INSERT INTO disposal_record (disposal_no, food_name, disposal_method, disposal_time, responsible_unit, status, created_time, updated_time) VALUES (#{disposalNo}, #{foodName}, #{disposalMethod}, #{disposalTime}, #{responsibleUnit}, #{status}, NOW(), NOW())")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(DisposalRecord entity);

    @Update("UPDATE disposal_record SET disposal_no = #{disposalNo}, food_name = #{foodName}, disposal_method = #{disposalMethod}, disposal_time = #{disposalTime}, responsible_unit = #{responsibleUnit}, status = #{status}, updated_time = NOW() WHERE id = #{id}")
    int update(DisposalRecord entity);

    @Delete("DELETE FROM disposal_record WHERE id = #{id}")
    int deleteById(Long id);

    @Select("SELECT COUNT(*) FROM disposal_record")
    long countAll();

    @Update("UPDATE disposal_record SET status = #{status}, updated_time = NOW() WHERE id = #{id}")
    int updateStatus(@Param("id") Long id, @Param("status") String status);
}






