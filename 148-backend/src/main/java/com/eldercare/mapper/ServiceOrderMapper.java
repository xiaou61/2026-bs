package com.eldercare.mapper;

import com.eldercare.entity.ServiceOrder;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface ServiceOrderMapper {
    @Select({
        "<script>",
        "SELECT * FROM service_order",
        "<where>",
        "<if test='keyword != null and keyword != \"\"'> AND (order_no LIKE CONCAT('%',#{keyword},'%') OR service_subject LIKE CONCAT('%',#{keyword},'%') OR service_address LIKE CONCAT('%',#{keyword},'%') OR dispatcher_name LIKE CONCAT('%',#{keyword},'%'))</if>",
        "<if test='status != null and status != \"\"'> AND status = #{status}</if>",
        "</where>",
        "ORDER BY id DESC",
        "</script>"
    })
    List<ServiceOrder> selectPage(@Param("keyword") String keyword, @Param("status") String status);

    @Select("SELECT * FROM service_order WHERE id = #{id}")
    ServiceOrder selectById(Long id);

    @Insert("INSERT INTO service_order (order_no, service_subject, service_address, visit_time, dispatcher_name, status, created_time, updated_time) VALUES (#{orderNo}, #{serviceSubject}, #{serviceAddress}, #{visitTime}, #{dispatcherName}, #{status}, NOW(), NOW())")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(ServiceOrder entity);

    @Update("UPDATE service_order SET order_no = #{orderNo}, service_subject = #{serviceSubject}, service_address = #{serviceAddress}, visit_time = #{visitTime}, dispatcher_name = #{dispatcherName}, status = #{status}, updated_time = NOW() WHERE id = #{id}")
    int update(ServiceOrder entity);

    @Delete("DELETE FROM service_order WHERE id = #{id}")
    int deleteById(Long id);

    @Select("SELECT COUNT(*) FROM service_order")
    long countAll();

    @Update("UPDATE service_order SET status = #{status}, updated_time = NOW() WHERE id = #{id}")
    int updateStatus(@Param("id") Long id, @Param("status") String status);
}
