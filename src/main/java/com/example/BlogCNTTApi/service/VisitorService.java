package com.example.BlogCNTTApi.service;

import com.example.BlogCNTTApi.dto.VisitorDTO;
import com.example.BlogCNTTApi.entity.VisitorEntity;
import com.example.BlogCNTTApi.repository.VisitorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.sql.Timestamp;

@Service
public class VisitorService {

    @Autowired
    VisitorRepository visitorRepository;

    public void createVisitor(VisitorDTO visitorDTO){
        VisitorEntity newVisitor = new VisitorEntity();
        newVisitor.setIpAddress(visitorDTO.getIpAddress());
        newVisitor.setUserAgent(visitorDTO.getUserAgent());
        newVisitor.setTimestamp(new Timestamp(new java.util.Date().getTime()));
        visitorRepository.save(newVisitor);
    }
}
