package com.example.BlogCNTTApi.service;

import com.example.BlogCNTTApi.dto.ContactDTO;
import com.example.BlogCNTTApi.entity.ConntactEntity;
import com.example.BlogCNTTApi.repository.ContactRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ContactService {

    @Autowired
    ContactRepository contactRepository;


    public void createContact(ContactDTO contact){
        ConntactEntity newConntact = new ConntactEntity();
        newConntact.setEmail(contact.getEmail());
        newConntact.setName(contact.getName());
        newConntact.setMessage(contact.getMessage());
        newConntact.setSubject(contact.getSubject());
        contactRepository.save(newConntact);
    }
}
