package com.livecommerce.mapper;

import com.livecommerce.entity.LiveChannel;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface LiveChannelMapper {
    @Select({
        "<script>",
        "SELECT * FROM live_channel",
        "<where>",
        "<if test='keyword != null and keyword != \"\"'> AND (channel_name LIKE CONCAT('%',#{keyword},'%') OR platform_name LIKE CONCAT('%',#{keyword},'%') OR account_no LIKE CONCAT('%',#{keyword},'%') OR owner_name LIKE CONCAT('%',#{keyword},'%'))</if>",
        "<if test='status != null and status != \"\"'> AND status = #{status}</if>",
        "</where>",
        "ORDER BY id DESC",
        "</script>"
    })
    List<LiveChannel> selectPage(@Param("keyword") String keyword, @Param("status") String status);

    @Select("SELECT * FROM live_channel WHERE id = #{id}")
    LiveChannel selectById(Long id);

    @Insert("INSERT INTO live_channel (channel_name, platform_name, account_no, owner_name, fans_count, contact_phone, status, created_time, updated_time) VALUES (#{channelName}, #{platformName}, #{accountNo}, #{ownerName}, #{fansCount}, #{contactPhone}, #{status}, NOW(), NOW())")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(LiveChannel entity);

    @Update("UPDATE live_channel SET channel_name = #{channelName}, platform_name = #{platformName}, account_no = #{accountNo}, owner_name = #{ownerName}, fans_count = #{fansCount}, contact_phone = #{contactPhone}, status = #{status}, updated_time = NOW() WHERE id = #{id}")
    int update(LiveChannel entity);

    @Delete("DELETE FROM live_channel WHERE id = #{id}")
    int deleteById(Long id);

    @Select("SELECT COUNT(*) FROM live_channel")
    long countAll();

    @Update("UPDATE live_channel SET status = #{status}, updated_time = NOW() WHERE id = #{id}")
    int updateStatus(@Param("id") Long id, @Param("status") String status);
}
