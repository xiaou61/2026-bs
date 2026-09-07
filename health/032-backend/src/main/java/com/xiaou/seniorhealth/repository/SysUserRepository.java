package com.xiaou.seniorhealth.repository;

import com.xiaou.seniorhealth.domain.SysUser;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface SysUserRepository extends CrudRepository<SysUser, Long> {
    @Query("select * from sys_user where username = :username limit 1")
    Optional<SysUser> findByUsername(String username);
}
