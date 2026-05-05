package com.esgreport.mapper;

import com.esgreport.entity.ScoreModel;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface ScoreModelMapper {
    @Select({
        "<script>",
        "SELECT * FROM score_model",
        "<where>",
        "<if test='keyword != null and keyword != \"\"'> AND (model_no LIKE CONCAT('%',#{keyword},'%') OR model_name LIKE CONCAT('%',#{keyword},'%') OR dimension_name LIKE CONCAT('%',#{keyword},'%') OR score_method LIKE CONCAT('%',#{keyword},'%'))</if>",
        "<if test='status != null and status != \"\"'> AND status = #{status}</if>",
        "</where>",
        "ORDER BY id DESC",
        "</script>"
    })
    List<ScoreModel> selectPage(@Param("keyword") String keyword, @Param("status") String status);

    @Select("SELECT * FROM score_model WHERE id = #{id}")
    ScoreModel selectById(Long id);

    @Insert("INSERT INTO score_model (model_no, model_name, dimension_name, score_method, weight_value, status, created_time, updated_time) VALUES (#{modelNo}, #{modelName}, #{dimensionName}, #{scoreMethod}, #{weightValue}, #{status}, NOW(), NOW())")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(ScoreModel entity);

    @Update("UPDATE score_model SET model_no = #{modelNo}, model_name = #{modelName}, dimension_name = #{dimensionName}, score_method = #{scoreMethod}, weight_value = #{weightValue}, status = #{status}, updated_time = NOW() WHERE id = #{id}")
    int update(ScoreModel entity);

    @Delete("DELETE FROM score_model WHERE id = #{id}")
    int deleteById(Long id);

    @Select("SELECT COUNT(*) FROM score_model")
    long countAll();

    @Update("UPDATE score_model SET status = #{status}, updated_time = NOW() WHERE id = #{id}")
    int updateStatus(@Param("id") Long id, @Param("status") String status);
}
