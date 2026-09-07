package com.vehicleclaim.mapper;

import com.vehicleclaim.entity.ProgressTrack;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface ProgressTrackMapper {
    @Select({
        "<script>",
        "SELECT * FROM progress_track",
        "<where>",
        "<if test='keyword != null and keyword != \"\"'> AND (track_no LIKE CONCAT('%',#{keyword},'%') OR report_no LIKE CONCAT('%',#{keyword},'%') OR current_node LIKE CONCAT('%',#{keyword},'%') OR handle_dept LIKE CONCAT('%',#{keyword},'%'))</if>",
        "<if test='status != null and status != \"\"'> AND status = #{status}</if>",
        "</where>",
        "ORDER BY id DESC",
        "</script>"
    })
    List<ProgressTrack> selectPage(@Param("keyword") String keyword, @Param("status") String status);

    @Select("SELECT * FROM progress_track WHERE id = #{id}")
    ProgressTrack selectById(Long id);

    @Insert("INSERT INTO progress_track (track_no, report_no, current_node, handle_dept, expected_finish_time, status, created_time, updated_time) VALUES (#{trackNo}, #{reportNo}, #{currentNode}, #{handleDept}, #{expectedFinishTime}, #{status}, NOW(), NOW())")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(ProgressTrack entity);

    @Update("UPDATE progress_track SET track_no = #{trackNo}, report_no = #{reportNo}, current_node = #{currentNode}, handle_dept = #{handleDept}, expected_finish_time = #{expectedFinishTime}, status = #{status}, updated_time = NOW() WHERE id = #{id}")
    int update(ProgressTrack entity);

    @Delete("DELETE FROM progress_track WHERE id = #{id}")
    int deleteById(Long id);

    @Select("SELECT COUNT(*) FROM progress_track")
    long countAll();

    @Update("UPDATE progress_track SET status = #{status}, updated_time = NOW() WHERE id = #{id}")
    int updateStatus(@Param("id") Long id, @Param("status") String status);
}
