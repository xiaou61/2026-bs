package com.xiaou.bike.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xiaou.bike.entity.UserWallet;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

import java.math.BigDecimal;

/**
 * 用户钱包Mapper接口
 */
@Mapper
public interface UserWalletMapper extends BaseMapper<UserWallet> {

    /**
     * 扣减余额
     */
    @Update("UPDATE user_wallet SET balance = balance - #{amount} WHERE user_id = #{userId} AND balance >= #{amount}")
    int deductBalance(@Param("userId") Long userId, @Param("amount") BigDecimal amount);

    /**
     * 增加余额
     */
    @Update("UPDATE user_wallet SET balance = balance + #{amount} WHERE user_id = #{userId}")
    int addBalance(@Param("userId") Long userId, @Param("amount") BigDecimal amount);
}
