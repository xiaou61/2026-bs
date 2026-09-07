package com.privacyauth.mapper;

import com.privacyauth.entity.PersonalDataItem;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface PersonalDataItemMapper {
    @Select({
        "<script>",
        "SELECT * FROM personal_data_item",
        "<where>",
        "<if test='keyword != null and keyword != \"\"'> AND (item_name LIKE CONCAT('%',#{keyword},'%') OR item_code LIKE CONCAT('%',#{keyword},'%') OR category_name LIKE CONCAT('%',#{keyword},'%') OR sensitive_level LIKE CONCAT('%',#{keyword},'%'))</if>",
        "<if test='status != null and status != \"\"'> AND status = #{status}</if>",
        "</where>",
        "ORDER BY id DESC",
        "</script>"
    })
    List<PersonalDataItem> selectPage(@Param("keyword") String keyword, @Param("status") String status);

    @Select("SELECT * FROM personal_data_item WHERE id = #{id}")
    PersonalDataItem selectById(Long id);

    @Insert("INSERT INTO personal_data_item (item_name, item_code, category_name, sensitive_level, retention_days, owner_name, status, created_time, updated_time) VALUES (#{itemName}, #{itemCode}, #{categoryName}, #{sensitiveLevel}, #{retentionDays}, #{ownerName}, #{status}, NOW(), NOW())")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(PersonalDataItem entity);

    @Update("UPDATE personal_data_item SET item_name = #{itemName}, item_code = #{itemCode}, category_name = #{categoryName}, sensitive_level = #{sensitiveLevel}, retention_days = #{retentionDays}, owner_name = #{ownerName}, status = #{status}, updated_time = NOW() WHERE id = #{id}")
    int update(PersonalDataItem entity);

    @Delete("DELETE FROM personal_data_item WHERE id = #{id}")
    int deleteById(Long id);

    @Select("SELECT COUNT(*) FROM personal_data_item")
    long countAll();

    @Update("UPDATE personal_data_item SET status = #{status}, updated_time = NOW() WHERE id = #{id}")
    int updateStatus(@Param("id") Long id, @Param("status") String status);
}
