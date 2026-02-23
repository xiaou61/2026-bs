package com.inventory.mapper;

import com.inventory.entity.Customer;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface CustomerMapper {

    @Select("<script>" +
            "select * from customer where 1=1 " +
            "<if test='name!=null and name!=\"\"'>and name like concat('%',#{name},'%')</if> " +
            "<if test='contactPerson!=null and contactPerson!=\"\"'>and contact_person like concat('%',#{contactPerson},'%')</if> " +
            "<if test='status!=null'>and status=#{status}</if> " +
            "order by id desc" +
            "</script>")
    List<Customer> selectPageList(@Param("name") String name, @Param("contactPerson") String contactPerson, @Param("status") Integer status);

    @Select("select * from customer where id=#{id}")
    Customer selectById(@Param("id") Long id);

    @Select("select * from customer where customer_no=#{customerNo}")
    Customer selectByNo(@Param("customerNo") String customerNo);

    @Select("select * from customer where status=1 order by id desc")
    List<Customer> selectEnabledList();

    @Select("select count(1) from customer")
    Long countAll();

    @Insert("insert into customer(customer_no,name,contact_person,phone,address,status) values(#{customerNo},#{name},#{contactPerson},#{phone},#{address},#{status})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(Customer customer);

    @Update("update customer set customer_no=#{customerNo},name=#{name},contact_person=#{contactPerson},phone=#{phone},address=#{address},status=#{status} where id=#{id}")
    int update(Customer customer);

    @Delete("delete from customer where id=#{id}")
    int deleteById(@Param("id") Long id);
}
