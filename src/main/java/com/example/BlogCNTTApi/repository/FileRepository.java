package com.example.BlogCNTTApi.repository;

import com.example.BlogCNTTApi.entity.FileEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FileRepository extends JpaRepository<FileEntity, String> {
}
