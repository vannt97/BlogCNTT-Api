package com.example.BlogCNTTApi.repository;


import com.example.BlogCNTTApi.entity.RoleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<RoleEntity,Long> {

    Optional<RoleEntity> findById(long id);

    List<RoleEntity> findAll();
}
