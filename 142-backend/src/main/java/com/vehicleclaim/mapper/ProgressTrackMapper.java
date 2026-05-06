package com.vehicleclaim.mapper;

import com.vehicleclaim.entity.ProgressTrack;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface ProgressTrackMapper {
    @Select({
        "<script>",
        "SELECT * FROM patent_record",
        "<where>",
        "<if test='keyword != null and keyword != \"\"'> AND (patent_no LIKE CONCAT('%',#{keyword},'%') OR project_no LIKE CONCAT('%',#{keyword},'%') OR patent_name LIKE CONCAT('%',#{keyword},'%') OR applicant_name LIKE CONCAT('%',#{keyword},'%'))</if>",
        "<if test='status != null and status != \"\"'> AND status = #{status}</if>",
        "</where>",
        "ORDER BY id DESC",
        "</script>"
    })
    List<ProgressTrack> selectPage(@Param("keyword") String keyword, @Param("status") String status);

    @Select("SELECT * FROM patent_record WHERE id = #{id}")
    ProgressTrack selectById(Long id);

    @Insert("INSERT INTO patent_record (patent_no, project_no, patent_name, applicant_name, grant_time, status, created_time, updated_time) VALUES (#{patentNo}, #{projectNo}, #{patentName}, #{applicantName}, #{grantTime}, #{status}, NOW(), NOW())")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(ProgressTrack entity);

    @Update("UPDATE patent_record SET patent_no = #{patentNo}, project_no = #{projectNo}, patent_name = #{patentName}, applicant_name = #{applicantName}, grant_time = #{grantTime}, status = #{status}, updated_time = NOW() WHERE id = #{id}")
    int update(ProgressTrack entity);

    @Delete("DELETE FROM patent_record WHERE id = #{id}")
    int deleteById(Long id);

    @Select("SELECT COUNT(*) FROM patent_record")
    long countAll();

    @Update("UPDATE patent_record SET status = #{status}, updated_time = NOW() WHERE id = #{id}")
    int updateStatus(@Param("id") Long id, @Param("status") String status);
}




