package com.inventory.mapper;

import com.inventory.entity.Product;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface ProductMapper {

    @Select("<script>" +
            "select * from product where 1=1 " +
            "<if test='name!=null and name!=\"\"'>and name like concat('%',#{name},'%')</if> " +
            "<if test='productNo!=null and productNo!=\"\"'>and product_no like concat('%',#{productNo},'%')</if> " +
            "<if test='categoryId!=null'>and category_id=#{categoryId}</if> " +
            "<if test='status!=null'>and status=#{status}</if> " +
            "order by id desc" +
            "</script>")
    List<Product> selectPageList(@Param("name") String name, @Param("productNo") String productNo, @Param("categoryId") Long categoryId, @Param("status") Integer status);

    @Select("select * from product where id=#{id}")
    Product selectById(@Param("id") Long id);

    @Select("select * from product where product_no=#{productNo}")
    Product selectByNo(@Param("productNo") String productNo);

    @Select("select * from product where status=1 order by id desc")
    List<Product> selectEnabledList();

    @Select("select count(1) from product")
    Long countAll();

    @Insert("insert into product(product_no,name,category_id,spec,unit,cost_price,sale_price,stock,stock_warn,status) values(#{productNo},#{name},#{categoryId},#{spec},#{unit},#{costPrice},#{salePrice},#{stock},#{stockWarn},#{status})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(Product product);

    @Update("update product set product_no=#{productNo},name=#{name},category_id=#{categoryId},spec=#{spec},unit=#{unit},cost_price=#{costPrice},sale_price=#{salePrice},stock=#{stock},stock_warn=#{stockWarn},status=#{status} where id=#{id}")
    int update(Product product);

    @Update("update product set stock=#{stock} where id=#{id}")
    int updateStock(@Param("id") Long id, @Param("stock") Integer stock);

    @Delete("delete from product where id=#{id}")
    int deleteById(@Param("id") Long id);

    @Select("select count(1) from product where status=1 and stock<=stock_warn")
    Long countWarnProducts();
}
