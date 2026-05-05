package com.esgreport.mapper;

import com.esgreport.entity.EsgScore;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface EsgScoreMapper {
    @Select({
        "<script>",
        "SELECT * FROM esg_score",
        "<where>",
        "<if test='keyword != null and keyword != \"\"'> AND (score_no LIKE CONCAT('%',#{keyword},'%') OR company_name LIKE CONCAT('%',#{keyword},'%'))</if>",
        "<if test='status != null and status != \"\"'> AND status = #{status}</if>",
        "</where>",
        "ORDER BY id DESC",
        "</script>"
    })
    List<EsgScore> selectPage(@Param("keyword") String keyword, @Param("status") String status);

    @Select("SELECT * FROM esg_score WHERE id = #{id}")
    EsgScore selectById(Long id);

    @Insert("INSERT INTO esg_score (score_no, company_name, environment_score, social_score, governance_score, status, created_time, updated_time) VALUES (#{scoreNo}, #{companyName}, #{environmentScore}, #{socialScore}, #{governanceScore}, #{status}, NOW(), NOW())")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(EsgScore entity);

    @Update("UPDATE esg_score SET score_no = #{scoreNo}, company_name = #{companyName}, environment_score = #{environmentScore}, social_score = #{socialScore}, governance_score = #{governanceScore}, status = #{status}, updated_time = NOW() WHERE id = #{id}")
    int update(EsgScore entity);

    @Delete("DELETE FROM esg_score WHERE id = #{id}")
    int deleteById(Long id);

    @Select("SELECT COUNT(*) FROM esg_score")
    long countAll();

    @Update("UPDATE esg_score SET status = #{status}, updated_time = NOW() WHERE id = #{id}")
    int updateStatus(@Param("id") Long id, @Param("status") String status);
}
