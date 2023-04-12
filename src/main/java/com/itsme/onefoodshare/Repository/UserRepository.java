package com.itsme.onefoodshare.Repository;

import com.itsme.onefoodshare.entity.User;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@EnableJpaRepositories
public interface UserRepository extends JpaRepository<User, Long> {
    @EntityGraph(attributePaths = "authorities")	// Eager(즉시)조회로 authorites 정보를 같이 가져온다.
        // username을 기준으로 User 정보를 가져올때 권한 정보도 같이 가져온다.
    Optional<User> findOneWithAuthoritiesByUsername(String username);

    Optional<User> findByMemberId(String username);

    Optional<User> findByUsername(String membername);
}
