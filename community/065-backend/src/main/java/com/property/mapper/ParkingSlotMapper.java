package com.property.mapper;

import com.property.entity.ParkingSlot;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface ParkingSlotMapper {

    @Select("<script>" +
            "select * from parking_slot where 1=1 " +
            "<if test='slotNo!=null and slotNo!=\"\"'>and slot_no like concat('%',#{slotNo},'%')</if> " +
            "<if test='status!=null'>and status=#{status}</if> " +
            "<if test='ownerId!=null'>and owner_id=#{ownerId}</if> " +
            "order by id desc" +
            "</script>")
    List<ParkingSlot> selectPageList(@Param("slotNo") String slotNo, @Param("status") Integer status, @Param("ownerId") Long ownerId);

    @Select("select * from parking_slot where status=0 order by id desc")
    List<ParkingSlot> selectFreeList();

    @Select("select * from parking_slot where id=#{id}")
    ParkingSlot selectById(@Param("id") Long id);

    @Insert("insert into parking_slot(slot_no,location,owner_id,status) values(#{slotNo},#{location},#{ownerId},#{status})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(ParkingSlot slot);

    @Update("update parking_slot set slot_no=#{slotNo},location=#{location},owner_id=#{ownerId},status=#{status} where id=#{id}")
    int update(ParkingSlot slot);

    @Delete("delete from parking_slot where id=#{id}")
    int deleteById(@Param("id") Long id);
}
