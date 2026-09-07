package com.eldercare.mapper;

import com.eldercare.entity.FamilyVisit;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface FamilyVisitMapper {
    @Select({
        "<script>",
        "SELECT * FROM family_visit",
        "<where>",
        "<if test='keyword != null and keyword != \"\"'> AND (visit_no LIKE CONCAT('%',#{keyword},'%') OR elder_name LIKE CONCAT('%',#{keyword},'%') OR visit_subject LIKE CONCAT('%',#{keyword},'%') OR visit_method LIKE CONCAT('%',#{keyword},'%'))</if>",
        "<if test='status != null and status != \"\"'> AND status = #{status}</if>",
        "</where>",
        "ORDER BY id DESC",
        "</script>"
    })
    List<FamilyVisit> selectPage(@Param("keyword") String keyword, @Param("status") String status);

    @Select("SELECT * FROM family_visit WHERE id = #{id}")
    FamilyVisit selectById(Long id);

    @Insert("INSERT INTO family_visit (visit_no, elder_name, visit_subject, visit_method, visit_time, status, created_time, updated_time) VALUES (#{visitNo}, #{elderName}, #{visitSubject}, #{visitMethod}, #{visitTime}, #{status}, NOW(), NOW())")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(FamilyVisit entity);

    @Update("UPDATE family_visit SET visit_no = #{visitNo}, elder_name = #{elderName}, visit_subject = #{visitSubject}, visit_method = #{visitMethod}, visit_time = #{visitTime}, status = #{status}, updated_time = NOW() WHERE id = #{id}")
    int update(FamilyVisit entity);

    @Delete("DELETE FROM family_visit WHERE id = #{id}")
    int deleteById(Long id);

    @Select("SELECT COUNT(*) FROM family_visit")
    long countAll();

    @Update("UPDATE family_visit SET status = #{status}, updated_time = NOW() WHERE id = #{id}")
    int updateStatus(@Param("id") Long id, @Param("status") String status);
}
