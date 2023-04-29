package com.example.BlogCNTTApi.service;


import java.util.List;
import com.example.BlogCNTTApi.entity.RoleEntity;
import com.example.BlogCNTTApi.enums.ERole;
import com.example.BlogCNTTApi.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class RoleService {

    @Autowired
    RoleRepository roleRepo;

    public RoleEntity getRoleById(long id){
        Optional<RoleEntity> role = roleRepo.findById(id);
        return role.orElse(null);
    }


    public void createDataRoleAuto(){
        RoleEntity roleAdmin = new RoleEntity();
        roleAdmin.setName("admin");
        roleAdmin.setRoleName(ERole.ROLE_ADMIN.name());

        RoleEntity roleStaff = new RoleEntity();
        roleStaff.setName("staff");
        roleStaff.setRoleName(ERole.ROLE_STAFF.name());

        RoleEntity roleAnonymouse = new RoleEntity();
        roleAnonymouse.setName("anonymous");
        roleAnonymouse.setRoleName(ERole.ROLE_ANONYMOUS.name());

    }
    public List<RoleEntity> getRoles(){
        return roleRepo.findAll();
    }

}
