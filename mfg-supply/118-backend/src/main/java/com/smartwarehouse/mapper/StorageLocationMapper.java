package com.smartwarehouse.mapper;

import com.smartwarehouse.entity.StorageLocation;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface StorageLocationMapper {
    @Select({
        "<script>",
        "SELECT * FROM storage_location",
        "<where>",
        "<if test='keyword != null and keyword != \"\"'> AND (location_no LIKE CONCAT('%',#{keyword},'%') OR zone_name LIKE CONCAT('%',#{keyword},'%') OR location_type LIKE CONCAT('%',#{keyword},'%') OR heat_level LIKE CONCAT('%',#{keyword},'%'))</if>",
        "<if test='status != null and status != \"\"'> AND status = #{status}</if>",
        "</where>",
        "ORDER BY id DESC",
        "</script>"
    })
    List<StorageLocation> selectPage(@Param("keyword") String keyword, @Param("status") String status);

    @Select("SELECT * FROM storage_location WHERE id = #{id}")
    StorageLocation selectById(Long id);

    @Insert("INSERT INTO storage_location (location_no, zone_name, location_type, max_weight, volume_capacity, heat_level, status, created_time, updated_time) VALUES (#{locationNo}, #{zoneName}, #{locationType}, #{maxWeight}, #{volumeCapacity}, #{heatLevel}, #{status}, NOW(), NOW())")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(StorageLocation entity);

    @Update("UPDATE storage_location SET location_no = #{locationNo}, zone_name = #{zoneName}, location_type = #{locationType}, max_weight = #{maxWeight}, volume_capacity = #{volumeCapacity}, heat_level = #{heatLevel}, status = #{status}, updated_time = NOW() WHERE id = #{id}")
    int update(StorageLocation entity);

    @Delete("DELETE FROM storage_location WHERE id = #{id}")
    int deleteById(Long id);

    @Select("SELECT COUNT(*) FROM storage_location")
    long countAll();

    @Update("UPDATE storage_location SET status = #{status}, updated_time = NOW() WHERE id = #{id}")
    int updateStatus(@Param("id") Long id, @Param("status") String status);
}
