package com.twinpark.mapper;

import com.twinpark.entity.TwinDevice;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface TwinDeviceMapper {
    @Select({
        "<script>",
        "SELECT * FROM twin_device",
        "<where>",
        "<if test='keyword != null and keyword != \"\"'> AND (device_no LIKE CONCAT('%',#{keyword},'%') OR device_name LIKE CONCAT('%',#{keyword},'%') OR building_name LIKE CONCAT('%',#{keyword},'%') OR device_type LIKE CONCAT('%',#{keyword},'%'))</if>",
        "<if test='status != null and status != \"\"'> AND status = #{status}</if>",
        "</where>",
        "ORDER BY id DESC",
        "</script>"
    })
    List<TwinDevice> selectPage(@Param("keyword") String keyword, @Param("status") String status);

    @Select("SELECT * FROM twin_device WHERE id = #{id}")
    TwinDevice selectById(Long id);

    @Insert("INSERT INTO twin_device (device_no, device_name, building_name, device_type, model_no, health_score, status, created_time, updated_time) VALUES (#{deviceNo}, #{deviceName}, #{buildingName}, #{deviceType}, #{modelNo}, #{healthScore}, #{status}, NOW(), NOW())")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(TwinDevice entity);

    @Update("UPDATE twin_device SET device_no = #{deviceNo}, device_name = #{deviceName}, building_name = #{buildingName}, device_type = #{deviceType}, model_no = #{modelNo}, health_score = #{healthScore}, status = #{status}, updated_time = NOW() WHERE id = #{id}")
    int update(TwinDevice entity);

    @Delete("DELETE FROM twin_device WHERE id = #{id}")
    int deleteById(Long id);

    @Select("SELECT COUNT(*) FROM twin_device")
    long countAll();

    @Update("UPDATE twin_device SET status = #{status}, updated_time = NOW() WHERE id = #{id}")
    int updateStatus(@Param("id") Long id, @Param("status") String status);
}
