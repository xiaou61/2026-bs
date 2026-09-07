package com.inventory.mapper;

import com.inventory.entity.StockRecord;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface StockRecordMapper {

    @Select("<script>" +
            "select * from stock_record where 1=1 " +
            "<if test='bizType!=null and bizType!=\"\"'>and biz_type=#{bizType}</if> " +
            "<if test='bizNo!=null and bizNo!=\"\"'>and biz_no like concat('%',#{bizNo},'%')</if> " +
            "<if test='productId!=null'>and product_id=#{productId}</if> " +
            "order by id desc" +
            "</script>")
    List<StockRecord> selectPageList(@Param("bizType") String bizType, @Param("bizNo") String bizNo, @Param("productId") Long productId);

    @Insert("insert into stock_record(biz_type,biz_no,product_id,change_qty,before_stock,after_stock,remark,operator_id) values(#{bizType},#{bizNo},#{productId},#{changeQty},#{beforeStock},#{afterStock},#{remark},#{operatorId})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(StockRecord record);
}
