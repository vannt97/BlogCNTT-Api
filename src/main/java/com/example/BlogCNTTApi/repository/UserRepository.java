package com.example.BlogCNTTApi.repository;

import com.example.BlogCNTTApi.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity,Long>, CrudRepository<UserEntity,Long> {

    public List<UserEntity> findAll();

    public Optional<UserEntity> findBySlug(String slug);

    public Optional<UserEntity> findByEmail(String email);

    public boolean existsBySlug(String slug);

    public Optional<UserEntity> findById(long id);
}
