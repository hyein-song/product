package com.ssg.product.repository;

import com.ssg.product.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {

    Optional<UserEntity> findByUserNameAndUserType(String userName, String userType);

    Optional<UserEntity> findByIdAndUserStat(Long userId, String userStat);
}
