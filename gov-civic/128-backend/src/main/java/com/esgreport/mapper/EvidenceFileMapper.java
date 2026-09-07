package com.esgreport.mapper;

import com.esgreport.entity.EvidenceFile;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface EvidenceFileMapper {
    @Select({
        "<script>",
        "SELECT * FROM evidence_file",
        "<where>",
        "<if test='keyword != null and keyword != \"\"'> AND (evidence_no LIKE CONCAT('%',#{keyword},'%') OR company_name LIKE CONCAT('%',#{keyword},'%') OR file_name LIKE CONCAT('%',#{keyword},'%') OR file_type LIKE CONCAT('%',#{keyword},'%'))</if>",
        "<if test='status != null and status != \"\"'> AND status = #{status}</if>",
        "</where>",
        "ORDER BY id DESC",
        "</script>"
    })
    List<EvidenceFile> selectPage(@Param("keyword") String keyword, @Param("status") String status);

    @Select("SELECT * FROM evidence_file WHERE id = #{id}")
    EvidenceFile selectById(Long id);

    @Insert("INSERT INTO evidence_file (evidence_no, company_name, file_name, file_type, uploader_name, status, created_time, updated_time) VALUES (#{evidenceNo}, #{companyName}, #{fileName}, #{fileType}, #{uploaderName}, #{status}, NOW(), NOW())")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(EvidenceFile entity);

    @Update("UPDATE evidence_file SET evidence_no = #{evidenceNo}, company_name = #{companyName}, file_name = #{fileName}, file_type = #{fileType}, uploader_name = #{uploaderName}, status = #{status}, updated_time = NOW() WHERE id = #{id}")
    int update(EvidenceFile entity);

    @Delete("DELETE FROM evidence_file WHERE id = #{id}")
    int deleteById(Long id);

    @Select("SELECT COUNT(*) FROM evidence_file")
    long countAll();

    @Update("UPDATE evidence_file SET status = #{status}, updated_time = NOW() WHERE id = #{id}")
    int updateStatus(@Param("id") Long id, @Param("status") String status);
}
