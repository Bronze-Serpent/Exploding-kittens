package com.kittens.server.user.repository;

import com.kittens.server.user.entity.UserDynamicPassword;
import org.hibernate.query.spi.Limit;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserDynamicPasswordRepository extends JpaRepository<UserDynamicPassword, Long> {
    List<UserDynamicPassword> findByUserLoginOrderByCreatedDateDesc(String login, Pageable pageable);
}
