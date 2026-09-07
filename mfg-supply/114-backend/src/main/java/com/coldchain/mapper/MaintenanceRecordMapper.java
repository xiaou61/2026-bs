package com.coldchain.mapper;

import com.coldchain.entity.MaintenanceRecord;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface MaintenanceRecordMapper {
    @Select({
        "<script>",
        "SELECT * FROM maintenance_record",
        "<where>",
        "<if test='keyword != null and keyword != \"\"'> AND (maintenance_no LIKE CONCAT('%',#{keyword},'%') OR device_no LIKE CONCAT('%',#{keyword},'%') OR maintainer_name LIKE CONCAT('%',#{keyword},'%') OR result_status LIKE CONCAT('%',#{keyword},'%'))</if>",
        "<if test='status != null and status != \"\"'> AND status = #{status}</if>",
        "</where>",
        "ORDER BY id DESC",
        "</script>"
    })
    List<MaintenanceRecord> selectPage(@Param("keyword") String keyword, @Param("status") String status);

    @Select("SELECT * FROM maintenance_record WHERE id = #{id}")
    MaintenanceRecord selectById(Long id);

    @Insert("INSERT INTO maintenance_record (maintenance_no, device_no, maintainer_name, maintenance_type, result_status, detail_text, status, created_time, updated_time) VALUES (#{maintenanceNo}, #{deviceNo}, #{maintainerName}, #{maintenanceType}, #{resultStatus}, #{detailText}, #{status}, NOW(), NOW())")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(MaintenanceRecord entity);

    @Update("UPDATE maintenance_record SET maintenance_no = #{maintenanceNo}, device_no = #{deviceNo}, maintainer_name = #{maintainerName}, maintenance_type = #{maintenanceType}, result_status = #{resultStatus}, detail_text = #{detailText}, status = #{status}, updated_time = NOW() WHERE id = #{id}")
    int update(MaintenanceRecord entity);

    @Delete("DELETE FROM maintenance_record WHERE id = #{id}")
    int deleteById(Long id);

    @Select("SELECT COUNT(*) FROM maintenance_record")
    long countAll();

    @Update("UPDATE maintenance_record SET status = #{status}, updated_time = NOW() WHERE id = #{id}")
    int updateStatus(@Param("id") Long id, @Param("status") String status);

}
