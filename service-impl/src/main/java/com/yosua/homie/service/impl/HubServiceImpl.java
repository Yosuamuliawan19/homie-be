package com.yosua.homie.service.impl;

import com.yosua.homie.dao.UserRepository;
import com.yosua.homie.entity.constant.enums.ResponseCode;
import com.yosua.homie.entity.dao.Hub;
import com.yosua.homie.entity.dao.HubBuilder;
import com.yosua.homie.entity.dao.User;
import com.yosua.homie.libraries.exception.BusinessLogicException;
import com.yosua.homie.rest.web.model.request.HubsRequest;
import com.yosua.homie.service.api.HubService;
import org.apache.commons.lang3.Validate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class HubServiceImpl implements HubService {

    private static final Logger LOGGER = LoggerFactory.getLogger(HubServiceImpl.class);

    @Autowired
    UserRepository userRepository;

    @Override
    public User addHubs(HubsRequest hubsRequests, String userID) {
        Validate.notNull(hubsRequests, "hubsRequest is required");
        User existingUser = userRepository.findUserById(userID);
        if(Objects.isNull(existingUser))
        {
            throw new BusinessLogicException(ResponseCode.USER_DOES_NOT_EXIST.getCode(),
                    ResponseCode.USER_DOES_NOT_EXIST.getMessage());
        }

        List<Hub> existingHubs = existingUser.getHubs();
        List<String> existingIP = new ArrayList<>();
        List<String> IPToBeAdded = hubsRequests.getIpAddress();
        if(!Objects.isNull(existingHubs) &&!existingHubs.isEmpty()) {
            for (Hub hubs : existingHubs) {
                existingIP.add(hubs.getIpAddress());
            }
        }
        if(!existingIP.isEmpty())
        {
            for(String existingIPs: existingIP){
                for(String IPsToBeAdded: IPToBeAdded) {
                    if(existingIPs.equals(IPsToBeAdded))
                    {
                        throw new BusinessLogicException(ResponseCode.DUPLICATE_DATA.getCode(),
                                ResponseCode.DUPLICATE_DATA.getMessage());
                    }
                }
            }
        }
        if(!Objects.isNull(existingHubs) &&!existingHubs.isEmpty()) {
            existingHubs.addAll(toHubs(hubsRequests));
            existingUser.setHubs(existingHubs);
        }
        else{
            existingUser.setHubs(toHubs(hubsRequests));
        }
        try{
            return userRepository.save(existingUser);
        } catch (Exception e) {
            LOGGER.warn("Failed to update User");
            throw new BusinessLogicException(ResponseCode.SYSTEM_ERROR.getCode(),
                    "Failed to update User");
        }
    }

    @Override
    public User editHubs(String userID, String IPAddress, String updatedPhysicalAddress) {
        Validate.notNull(IPAddress,"IP Address to be updated is required");
        Validate.notNull(updatedPhysicalAddress,"Physical Address to be updated is required");
        User user = userRepository.findUserById(userID);
        List<Hub> userHubs = user.getHubs();
        Boolean isIPFound = false;
        for(Hub hubs: userHubs){
            if(hubs.getIpAddress().equals(IPAddress))
            {
                hubs.setHubPhysicalAddress(updatedPhysicalAddress);
                isIPFound = true;
            }
        }
        if(!isIPFound) {
            throw new BusinessLogicException(ResponseCode.DATA_NOT_EXIST.getCode(),
                    ResponseCode.DATA_NOT_EXIST.getMessage());
        }
        try{
            return userRepository.save(user);
        } catch (Exception e) {
            LOGGER.warn("Failed to update User");
            throw new BusinessLogicException(ResponseCode.SYSTEM_ERROR.getCode(),
                    "Failed to update User");
        }
    }

    @Override
    public List<Hub> toHubs(HubsRequest hubsRequest){
        List<Hub> hubs = new ArrayList<>();
        List<String> IPAddress = hubsRequest.getIpAddress();
        List<String> physicalAddress = hubsRequest.getHubPhysicalAddress();
        if(IPAddress.size() == physicalAddress.size())
        {
            for(int i=0; i< IPAddress.size();i++)
            {
                hubs.add(new HubBuilder()
                        .withHubPhysicalAddress(physicalAddress.get(i))
                        .withIpAddress(IPAddress.get(i))
                        .build()
                );
            }
        }
        else
        {
            throw new BusinessLogicException(ResponseCode.BIND_ERROR.getCode(),
                    ResponseCode.BIND_ERROR.getMessage());
        }
        return hubs;
    }


}
