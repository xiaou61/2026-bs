package com.xiaou.mapper;

import com.xiaou.entity.Claim;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import java.util.List;

@Mapper
public interface ClaimMapper {
    List<Claim> findAll();
    
    Claim findById(@Param("id") Long id);
    
    List<Claim> findByClaimerId(@Param("claimerId") Long claimerId);
    
    List<Claim> findByOwnerId(@Param("ownerId") Long ownerId);
    
    List<Claim> findByItem(@Param("itemType") String itemType, @Param("itemId") Long itemId);
    
    int insert(Claim claim);
    
    int update(Claim claim);
    
    int deleteById(@Param("id") Long id);
}

