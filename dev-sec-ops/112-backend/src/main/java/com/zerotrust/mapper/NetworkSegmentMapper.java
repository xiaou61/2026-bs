package com.zerotrust.mapper;

import com.zerotrust.entity.NetworkSegment;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface NetworkSegmentMapper {
    @Select({
        "<script>",
        "SELECT * FROM network_segment",
        "<where>",
        "<if test='keyword != null and keyword != \"\"'> AND (segment_name LIKE CONCAT('%',#{keyword},'%') OR segment_code LIKE CONCAT('%',#{keyword},'%') OR cidr_block LIKE CONCAT('%',#{keyword},'%') OR security_zone LIKE CONCAT('%',#{keyword},'%'))</if>",
        "<if test='status != null and status != \"\"'> AND status = #{status}</if>",
        "</where>",
        "ORDER BY id DESC",
        "</script>"
    })
    List<NetworkSegment> selectPage(@Param("keyword") String keyword, @Param("status") String status);

    @Select("SELECT * FROM network_segment WHERE id = #{id}")
    NetworkSegment selectById(Long id);

    @Insert("INSERT INTO network_segment (segment_name, segment_code, cidr_block, security_zone, owner_name, status, created_time, updated_time) VALUES (#{segmentName}, #{segmentCode}, #{cidrBlock}, #{securityZone}, #{ownerName}, #{status}, NOW(), NOW())")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(NetworkSegment entity);

    @Update("UPDATE network_segment SET segment_name = #{segmentName}, segment_code = #{segmentCode}, cidr_block = #{cidrBlock}, security_zone = #{securityZone}, owner_name = #{ownerName}, status = #{status}, updated_time = NOW() WHERE id = #{id}")
    int update(NetworkSegment entity);

    @Delete("DELETE FROM network_segment WHERE id = #{id}")
    int deleteById(Long id);

    @Select("SELECT COUNT(*) FROM network_segment")
    long countAll();

    @Update("UPDATE network_segment SET status = #{status}, updated_time = NOW() WHERE id = #{id}")
    int updateStatus(@Param("id") Long id, @Param("status") String status);
}
