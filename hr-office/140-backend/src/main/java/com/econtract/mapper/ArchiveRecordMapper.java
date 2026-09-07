package com.econtract.mapper;

import com.econtract.entity.ArchiveRecord;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface ArchiveRecordMapper {
    @Select({
        "<script>",
        "SELECT * FROM archive_record",
        "<where>",
        "<if test='keyword != null and keyword != \"\"'> AND (archive_no LIKE CONCAT('%',#{keyword},'%') OR contract_title LIKE CONCAT('%',#{keyword},'%') OR archive_location LIKE CONCAT('%',#{keyword},'%') OR archivist_name LIKE CONCAT('%',#{keyword},'%'))</if>",
        "<if test='status != null and status != \"\"'> AND status = #{status}</if>",
        "</where>",
        "ORDER BY id DESC",
        "</script>"
    })
    List<ArchiveRecord> selectPage(@Param("keyword") String keyword, @Param("status") String status);

    @Select("SELECT * FROM archive_record WHERE id = #{id}")
    ArchiveRecord selectById(Long id);

    @Insert("INSERT INTO archive_record (archive_no, contract_title, archive_location, archivist_name, archive_date, status, created_time, updated_time) VALUES (#{archiveNo}, #{contractTitle}, #{archiveLocation}, #{archivistName}, #{archiveDate}, #{status}, NOW(), NOW())")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(ArchiveRecord entity);

    @Update("UPDATE archive_record SET archive_no = #{archiveNo}, contract_title = #{contractTitle}, archive_location = #{archiveLocation}, archivist_name = #{archivistName}, archive_date = #{archiveDate}, status = #{status}, updated_time = NOW() WHERE id = #{id}")
    int update(ArchiveRecord entity);

    @Delete("DELETE FROM archive_record WHERE id = #{id}")
    int deleteById(Long id);

    @Select("SELECT COUNT(*) FROM archive_record")
    long countAll();

    @Update("UPDATE archive_record SET status = #{status}, updated_time = NOW() WHERE id = #{id}")
    int updateStatus(@Param("id") Long id, @Param("status") String status);
}



