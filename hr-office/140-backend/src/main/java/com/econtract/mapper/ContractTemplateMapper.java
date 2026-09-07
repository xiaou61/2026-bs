package com.econtract.mapper;

import com.econtract.entity.ContractTemplate;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface ContractTemplateMapper {
    @Select({
        "<script>",
        "SELECT * FROM contract_template",
        "<where>",
        "<if test='keyword != null and keyword != \"\"'> AND (template_no LIKE CONCAT('%',#{keyword},'%') OR template_name LIKE CONCAT('%',#{keyword},'%') OR contract_type LIKE CONCAT('%',#{keyword},'%') OR maintainer_name LIKE CONCAT('%',#{keyword},'%'))</if>",
        "<if test='status != null and status != \"\"'> AND status = #{status}</if>",
        "</where>",
        "ORDER BY id DESC",
        "</script>"
    })
    List<ContractTemplate> selectPage(@Param("keyword") String keyword, @Param("status") String status);

    @Select("SELECT * FROM contract_template WHERE id = #{id}")
    ContractTemplate selectById(Long id);

    @Insert("INSERT INTO contract_template (template_no, template_name, contract_type, version_no, maintainer_name, status, created_time, updated_time) VALUES (#{templateNo}, #{templateName}, #{contractType}, #{versionNo}, #{maintainerName}, #{status}, NOW(), NOW())")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(ContractTemplate entity);

    @Update("UPDATE contract_template SET template_no = #{templateNo}, template_name = #{templateName}, contract_type = #{contractType}, version_no = #{versionNo}, maintainer_name = #{maintainerName}, status = #{status}, updated_time = NOW() WHERE id = #{id}")
    int update(ContractTemplate entity);

    @Delete("DELETE FROM contract_template WHERE id = #{id}")
    int deleteById(Long id);

    @Select("SELECT COUNT(*) FROM contract_template")
    long countAll();

    @Update("UPDATE contract_template SET status = #{status}, updated_time = NOW() WHERE id = #{id}")
    int updateStatus(@Param("id") Long id, @Param("status") String status);
}



