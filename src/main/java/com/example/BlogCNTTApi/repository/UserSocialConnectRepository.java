package com.example.BlogCNTTApi.repository;

import com.example.BlogCNTTApi.entity.UserEntity;
import com.example.BlogCNTTApi.entity.UserSocialConnectEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserSocialConnectRepository extends JpaRepository<UserSocialConnectEntity, Long>, CrudRepository<UserSocialConnectEntity,Long> {
    public void deleteByUser(UserEntity user);

    public void deleteAllByUser(UserEntity user);

    public Optional<UserSocialConnectEntity> findById(long id);
}
