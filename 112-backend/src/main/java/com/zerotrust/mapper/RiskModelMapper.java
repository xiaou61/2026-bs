package com.zerotrust.mapper;

import com.zerotrust.entity.RiskModel;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface RiskModelMapper {
    @Select({
        "<script>",
        "SELECT * FROM risk_model",
        "<where>",
        "<if test='keyword != null and keyword != \"\"'> AND (model_name LIKE CONCAT('%',#{keyword},'%') OR model_code LIKE CONCAT('%',#{keyword},'%') OR model_type LIKE CONCAT('%',#{keyword},'%') OR owner_name LIKE CONCAT('%',#{keyword},'%'))</if>",
        "<if test='status != null and status != \"\"'> AND status = #{status}</if>",
        "</where>",
        "ORDER BY id DESC",
        "</script>"
    })
    List<RiskModel> selectPage(@Param("keyword") String keyword, @Param("status") String status);

    @Select("SELECT * FROM risk_model WHERE id = #{id}")
    RiskModel selectById(Long id);

    @Insert("INSERT INTO risk_model (model_name, model_code, model_type, threshold_score, owner_name, status, created_time, updated_time) VALUES (#{modelName}, #{modelCode}, #{modelType}, #{thresholdScore}, #{ownerName}, #{status}, NOW(), NOW())")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(RiskModel entity);

    @Update("UPDATE risk_model SET model_name = #{modelName}, model_code = #{modelCode}, model_type = #{modelType}, threshold_score = #{thresholdScore}, owner_name = #{ownerName}, status = #{status}, updated_time = NOW() WHERE id = #{id}")
    int update(RiskModel entity);

    @Delete("DELETE FROM risk_model WHERE id = #{id}")
    int deleteById(Long id);

    @Select("SELECT COUNT(*) FROM risk_model")
    long countAll();

    @Update("UPDATE risk_model SET status = #{status}, updated_time = NOW() WHERE id = #{id}")
    int updateStatus(@Param("id") Long id, @Param("status") String status);
}
