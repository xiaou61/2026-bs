package com.researchfund.mapper;

import com.researchfund.entity.ResearchAchievement;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface ResearchAchievementMapper {
    @Select({
        "<script>",
        "SELECT * FROM research_achievement",
        "<where>",
        "<if test='keyword != null and keyword != \"\"'> AND (achievement_no LIKE CONCAT('%',#{keyword},'%') OR project_no LIKE CONCAT('%',#{keyword},'%') OR achievement_name LIKE CONCAT('%',#{keyword},'%') OR achievement_type LIKE CONCAT('%',#{keyword},'%'))</if>",
        "<if test='status != null and status != \"\"'> AND status = #{status}</if>",
        "</where>",
        "ORDER BY id DESC",
        "</script>"
    })
    List<ResearchAchievement> selectPage(@Param("keyword") String keyword, @Param("status") String status);

    @Select("SELECT * FROM research_achievement WHERE id = #{id}")
    ResearchAchievement selectById(Long id);

    @Insert("INSERT INTO research_achievement (achievement_no, project_no, achievement_name, achievement_type, owner_name, status, created_time, updated_time) VALUES (#{achievementNo}, #{projectNo}, #{achievementName}, #{achievementType}, #{ownerName}, #{status}, NOW(), NOW())")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(ResearchAchievement entity);

    @Update("UPDATE research_achievement SET achievement_no = #{achievementNo}, project_no = #{projectNo}, achievement_name = #{achievementName}, achievement_type = #{achievementType}, owner_name = #{ownerName}, status = #{status}, updated_time = NOW() WHERE id = #{id}")
    int update(ResearchAchievement entity);

    @Delete("DELETE FROM research_achievement WHERE id = #{id}")
    int deleteById(Long id);

    @Select("SELECT COUNT(*) FROM research_achievement")
    long countAll();

    @Update("UPDATE research_achievement SET status = #{status}, updated_time = NOW() WHERE id = #{id}")
    int updateStatus(@Param("id") Long id, @Param("status") String status);
}
