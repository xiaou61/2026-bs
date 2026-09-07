package com.bike.mapper;

import com.bike.entity.User;
import org.apache.ibatis.annotations.Param;
import java.math.BigDecimal;
import java.util.List;

public interface UserMapper {
    User findByUsername(@Param("username") String username);
    User findById(@Param("id") Long id);
    List<User> findList(@Param("username") String username, @Param("role") String role, @Param("status") Integer status);
    int insert(User user);
    int update(User user);
    int updateStatus(@Param("id") Long id, @Param("status") Integer status);
    int updatePassword(@Param("id") Long id, @Param("password") String password);
    int updateBalance(@Param("id") Long id, @Param("amount") BigDecimal amount);
    int updateDeposit(@Param("id") Long id, @Param("depositPaid") Integer depositPaid);
    int updateCreditScore(@Param("id") Long id, @Param("creditScore") Integer creditScore);
    int countTotal();
}
