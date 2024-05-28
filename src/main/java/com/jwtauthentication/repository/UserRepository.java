package com.jwtauthentication.repository;

import com.jwtauthentication.entities.UserInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface UserRepository extends JpaRepository<UserInfo,Long> {

    Optional <UserInfo> findByUsername(String username);
}
