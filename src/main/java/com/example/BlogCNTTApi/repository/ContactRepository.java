package com.example.BlogCNTTApi.repository;

import com.example.BlogCNTTApi.entity.ConntactEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ContactRepository extends JpaRepository<ConntactEntity, Long> {
}
