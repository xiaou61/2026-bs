package com.inventory.mapper;

import com.inventory.entity.Supplier;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface SupplierMapper {

    @Select("<script>" +
            "select * from supplier where 1=1 " +
            "<if test='name!=null and name!=\"\"'>and name like concat('%',#{name},'%')</if> " +
            "<if test='contactPerson!=null and contactPerson!=\"\"'>and contact_person like concat('%',#{contactPerson},'%')</if> " +
            "<if test='status!=null'>and status=#{status}</if> " +
            "order by id desc" +
            "</script>")
    List<Supplier> selectPageList(@Param("name") String name, @Param("contactPerson") String contactPerson, @Param("status") Integer status);

    @Select("select * from supplier where id=#{id}")
    Supplier selectById(@Param("id") Long id);

    @Select("select * from supplier where supplier_no=#{supplierNo}")
    Supplier selectByNo(@Param("supplierNo") String supplierNo);

    @Select("select * from supplier where status=1 order by id desc")
    List<Supplier> selectEnabledList();

    @Select("select count(1) from supplier")
    Long countAll();

    @Insert("insert into supplier(supplier_no,name,contact_person,phone,address,status) values(#{supplierNo},#{name},#{contactPerson},#{phone},#{address},#{status})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(Supplier supplier);

    @Update("update supplier set supplier_no=#{supplierNo},name=#{name},contact_person=#{contactPerson},phone=#{phone},address=#{address},status=#{status} where id=#{id}")
    int update(Supplier supplier);

    @Delete("delete from supplier where id=#{id}")
    int deleteById(@Param("id") Long id);
}
