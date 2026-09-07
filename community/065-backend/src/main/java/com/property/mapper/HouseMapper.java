package com.property.mapper;

import com.property.entity.House;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface HouseMapper {

    @Select("<script>" +
            "select * from house where 1=1 " +
            "<if test='buildingId!=null'>and building_id=#{buildingId}</if> " +
            "<if test='ownerId!=null'>and owner_id=#{ownerId}</if> " +
            "<if test='status!=null'>and status=#{status}</if> " +
            "<if test='keyword!=null and keyword!=\"\"'>and (unit_no like concat('%',#{keyword},'%') or room_no like concat('%',#{keyword},'%'))</if> " +
            "order by id desc" +
            "</script>")
    List<House> selectPageList(@Param("buildingId") Long buildingId, @Param("ownerId") Long ownerId, @Param("status") Integer status, @Param("keyword") String keyword);

    @Select("select * from house where status=1 order by id desc")
    List<House> selectEnabledList();

    @Select("select * from house where owner_id=#{ownerId} and status=1 order by id desc")
    List<House> selectByOwnerId(@Param("ownerId") Long ownerId);

    @Select("select * from house where id=#{id}")
    House selectById(@Param("id") Long id);

    @Select("select count(1) from house")
    Long countAll();

    @Insert("insert into house(building_id,unit_no,room_no,area,owner_id,status) values(#{buildingId},#{unitNo},#{roomNo},#{area},#{ownerId},#{status})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(House house);

    @Update("update house set building_id=#{buildingId},unit_no=#{unitNo},room_no=#{roomNo},area=#{area},owner_id=#{ownerId},status=#{status} where id=#{id}")
    int update(House house);

    @Delete("delete from house where id=#{id}")
    int deleteById(@Param("id") Long id);
}
