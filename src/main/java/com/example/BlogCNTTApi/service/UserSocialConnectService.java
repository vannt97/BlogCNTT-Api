package com.example.BlogCNTTApi.service;

import com.example.BlogCNTTApi.entity.UserEntity;
import com.example.BlogCNTTApi.entity.UserSocialConnectEntity;
import com.example.BlogCNTTApi.exceptions.ResourceNotFoundException;
import com.example.BlogCNTTApi.repository.UserSocialConnectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;

@Service
public class UserSocialConnectService {
    @Autowired
    UserSocialConnectRepository userSocialConnectRepository;

    public void removeUserSocialConnect(long id){
        try{
//            Optional<UserSocialConnectEntity> userSocialConnect = userSocialConnectRepository.findById(id);
//            if(userSocialConnect.isPresent()){
//                UserSocialConnectEntity userSocialConnectOk = userSocialConnect.get();
//                Set<UserSocialConnectEntity> orderItems = userSocialConnectOk.getUser().getUserSocialConnects();
//                for (UserSocialConnectEntity orderItem : orderItems) {
//                    order.removeOrderItem(orderItem);
//                }
//            }
//            userSocialConnect.getUser().
//            userSocialConnectRepository.deleteById(userSocialConnect.getId());

        }catch (Exception e){
            throw new ResourceNotFoundException("Không có link social " + "" + " trong dữ liệu");
        }
    }

}
