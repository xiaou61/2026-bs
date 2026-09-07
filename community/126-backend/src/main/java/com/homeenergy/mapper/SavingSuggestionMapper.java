package com.homeenergy.mapper;

import com.homeenergy.entity.SavingSuggestion;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface SavingSuggestionMapper {
    @Select({
        "<script>",
        "SELECT * FROM saving_suggestion",
        "<where>",
        "<if test='keyword != null and keyword != \"\"'> AND (suggestion_no LIKE CONCAT('%',#{keyword},'%') OR home_no LIKE CONCAT('%',#{keyword},'%') OR device_name LIKE CONCAT('%',#{keyword},'%') OR suggestion_type LIKE CONCAT('%',#{keyword},'%'))</if>",
        "<if test='status != null and status != \"\"'> AND status = #{status}</if>",
        "</where>",
        "ORDER BY id DESC",
        "</script>"
    })
    List<SavingSuggestion> selectPage(@Param("keyword") String keyword, @Param("status") String status);

    @Select("SELECT * FROM saving_suggestion WHERE id = #{id}")
    SavingSuggestion selectById(Long id);

    @Insert("INSERT INTO saving_suggestion (suggestion_no, home_no, device_name, suggestion_type, saving_kwh, analyst_name, status, created_time, updated_time) VALUES (#{suggestionNo}, #{homeNo}, #{deviceName}, #{suggestionType}, #{savingKwh}, #{analystName}, #{status}, NOW(), NOW())")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(SavingSuggestion entity);

    @Update("UPDATE saving_suggestion SET suggestion_no = #{suggestionNo}, home_no = #{homeNo}, device_name = #{deviceName}, suggestion_type = #{suggestionType}, saving_kwh = #{savingKwh}, analyst_name = #{analystName}, status = #{status}, updated_time = NOW() WHERE id = #{id}")
    int update(SavingSuggestion entity);

    @Delete("DELETE FROM saving_suggestion WHERE id = #{id}")
    int deleteById(Long id);

    @Select("SELECT COUNT(*) FROM saving_suggestion")
    long countAll();

    @Update("UPDATE saving_suggestion SET status = #{status}, updated_time = NOW() WHERE id = #{id}")
    int updateStatus(@Param("id") Long id, @Param("status") String status);
}
