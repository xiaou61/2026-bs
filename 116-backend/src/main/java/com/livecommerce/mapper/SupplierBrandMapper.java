package com.livecommerce.mapper;

import com.livecommerce.entity.SupplierBrand;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface SupplierBrandMapper {
    @Select({
        "<script>",
        "SELECT * FROM supplier_brand",
        "<where>",
        "<if test='keyword != null and keyword != \"\"'> AND (supplier_name LIKE CONCAT('%',#{keyword},'%') OR brand_name LIKE CONCAT('%',#{keyword},'%') OR contact_name LIKE CONCAT('%',#{keyword},'%') OR qualification_level LIKE CONCAT('%',#{keyword},'%'))</if>",
        "<if test='status != null and status != \"\"'> AND status = #{status}</if>",
        "</where>",
        "ORDER BY id DESC",
        "</script>"
    })
    List<SupplierBrand> selectPage(@Param("keyword") String keyword, @Param("status") String status);

    @Select("SELECT * FROM supplier_brand WHERE id = #{id}")
    SupplierBrand selectById(Long id);

    @Insert("INSERT INTO supplier_brand (supplier_name, brand_name, contact_name, contact_phone, qualification_level, cooperation_mode, status, created_time, updated_time) VALUES (#{supplierName}, #{brandName}, #{contactName}, #{contactPhone}, #{qualificationLevel}, #{cooperationMode}, #{status}, NOW(), NOW())")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(SupplierBrand entity);

    @Update("UPDATE supplier_brand SET supplier_name = #{supplierName}, brand_name = #{brandName}, contact_name = #{contactName}, contact_phone = #{contactPhone}, qualification_level = #{qualificationLevel}, cooperation_mode = #{cooperationMode}, status = #{status}, updated_time = NOW() WHERE id = #{id}")
    int update(SupplierBrand entity);

    @Delete("DELETE FROM supplier_brand WHERE id = #{id}")
    int deleteById(Long id);

    @Select("SELECT COUNT(*) FROM supplier_brand")
    long countAll();

    @Update("UPDATE supplier_brand SET status = #{status}, updated_time = NOW() WHERE id = #{id}")
    int updateStatus(@Param("id") Long id, @Param("status") String status);
}
