package com.programming.learning.mapper;

import com.programming.learning.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 用户Mapper接口
 */
@Mapper
public interface UserMapper {

    /**
     * 插入用户
     */
    int insert(User user);

    /**
     * 根据ID删除用户
     */
    int deleteById(Long id);

    /**
     * 更新用户
     */
    int update(User user);

    /**
     * 根据ID查询用户
     */
    User selectById(Long id);

    /**
     * 根据openId查询用户
     */
    User selectByOpenId(String openId);

    /**
     * 根据手机号查询用户
     */
    User selectByPhone(String phone);

    /**
     * 查询所有用户（分页）
     */
    List<User> selectAll(@Param("offset") Integer offset, @Param("limit") Integer limit);

    /**
     * 统计用户总数
     */
    Long countAll();

    /**
     * 更新用户积分
     */
    int updateScore(@Param("id") Long id, @Param("score") Integer score);

    /**
     * 更新用户等级
     */
    int updateLevel(@Param("id") Long id, @Param("level") Integer level);

    /**
     * 更新最后登录时间和IP
     */
    int updateLastLogin(@Param("id") Long id, @Param("lastLoginIp") String lastLoginIp);

    /**
     * 根据积分排行
     */
    List<User> selectByScoreRank(@Param("limit") Integer limit);
}
