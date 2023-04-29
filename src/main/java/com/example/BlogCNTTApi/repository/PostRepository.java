package com.example.BlogCNTTApi.repository;

import com.example.BlogCNTTApi.entity.PostEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PostRepository extends JpaRepository<PostEntity, Long> {

    boolean existsBySlug(String slug);

    Optional<PostEntity> findBySlug(String slug);


}
