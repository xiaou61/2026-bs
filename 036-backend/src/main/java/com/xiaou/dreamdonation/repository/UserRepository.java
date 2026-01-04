package com.xiaou.dreamdonation.repository;

import com.xiaou.dreamdonation.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository&lt;User, Long&gt; {
    Optional&lt;User&gt; findByUsername(String username);
    Optional&lt;User&gt; findByEmail(String email);
    boolean existsByUsername(String username);
    boolean existsByEmail(String email);
}
