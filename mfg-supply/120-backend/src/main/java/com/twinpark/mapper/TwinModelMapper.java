package com.twinpark.mapper;

import com.twinpark.entity.TwinModel;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface TwinModelMapper {
    @Select({
        "<script>",
        "SELECT * FROM twin_model",
        "<where>",
        "<if test='keyword != null and keyword != \"\"'> AND (model_no LIKE CONCAT('%',#{keyword},'%') OR model_name LIKE CONCAT('%',#{keyword},'%') OR model_type LIKE CONCAT('%',#{keyword},'%') OR device_name LIKE CONCAT('%',#{keyword},'%'))</if>",
        "<if test='status != null and status != \"\"'> AND status = #{status}</if>",
        "</where>",
        "ORDER BY id DESC",
        "</script>"
    })
    List<TwinModel> selectPage(@Param("keyword") String keyword, @Param("status") String status);

    @Select("SELECT * FROM twin_model WHERE id = #{id}")
    TwinModel selectById(Long id);

    @Insert("INSERT INTO twin_model (model_no, model_name, model_type, device_name, version_no, sync_time, status, created_time, updated_time) VALUES (#{modelNo}, #{modelName}, #{modelType}, #{deviceName}, #{versionNo}, #{syncTime}, #{status}, NOW(), NOW())")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(TwinModel entity);

    @Update("UPDATE twin_model SET model_no = #{modelNo}, model_name = #{modelName}, model_type = #{modelType}, device_name = #{deviceName}, version_no = #{versionNo}, sync_time = #{syncTime}, status = #{status}, updated_time = NOW() WHERE id = #{id}")
    int update(TwinModel entity);

    @Delete("DELETE FROM twin_model WHERE id = #{id}")
    int deleteById(Long id);

    @Select("SELECT COUNT(*) FROM twin_model")
    long countAll();

    @Update("UPDATE twin_model SET status = #{status}, updated_time = NOW() WHERE id = #{id}")
    int updateStatus(@Param("id") Long id, @Param("status") String status);
}
