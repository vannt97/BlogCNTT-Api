package com.example.BlogCNTTApi.repository;

import com.example.BlogCNTTApi.entity.VisitorEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VisitorRepository extends JpaRepository<VisitorEntity, Long> {
}
