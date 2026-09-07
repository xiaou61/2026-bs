package com.esgreport.mapper;

import com.esgreport.entity.DisclosureTemplate;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface DisclosureTemplateMapper {
    @Select({
        "<script>",
        "SELECT * FROM disclosure_template",
        "<where>",
        "<if test='keyword != null and keyword != \"\"'> AND (template_no LIKE CONCAT('%',#{keyword},'%') OR template_name LIKE CONCAT('%',#{keyword},'%') OR industry_name LIKE CONCAT('%',#{keyword},'%') OR version_name LIKE CONCAT('%',#{keyword},'%'))</if>",
        "<if test='status != null and status != \"\"'> AND status = #{status}</if>",
        "</where>",
        "ORDER BY id DESC",
        "</script>"
    })
    List<DisclosureTemplate> selectPage(@Param("keyword") String keyword, @Param("status") String status);

    @Select("SELECT * FROM disclosure_template WHERE id = #{id}")
    DisclosureTemplate selectById(Long id);

    @Insert("INSERT INTO disclosure_template (template_no, template_name, industry_name, version_name, owner_name, status, created_time, updated_time) VALUES (#{templateNo}, #{templateName}, #{industryName}, #{versionName}, #{ownerName}, #{status}, NOW(), NOW())")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(DisclosureTemplate entity);

    @Update("UPDATE disclosure_template SET template_no = #{templateNo}, template_name = #{templateName}, industry_name = #{industryName}, version_name = #{versionName}, owner_name = #{ownerName}, status = #{status}, updated_time = NOW() WHERE id = #{id}")
    int update(DisclosureTemplate entity);

    @Delete("DELETE FROM disclosure_template WHERE id = #{id}")
    int deleteById(Long id);

    @Select("SELECT COUNT(*) FROM disclosure_template")
    long countAll();

    @Update("UPDATE disclosure_template SET status = #{status}, updated_time = NOW() WHERE id = #{id}")
    int updateStatus(@Param("id") Long id, @Param("status") String status);
}
