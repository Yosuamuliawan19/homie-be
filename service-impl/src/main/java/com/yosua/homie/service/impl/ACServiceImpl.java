package com.yosua.homie.service.impl;

import com.yosua.homie.dao.ACRepository;
import com.yosua.homie.dao.UserRepository;
import com.yosua.homie.entity.constant.enums.DeviceStatus;
import com.yosua.homie.entity.constant.enums.ResponseCode;
import com.yosua.homie.entity.dao.AC;
import com.yosua.homie.entity.dao.ACBuilder;
import com.yosua.homie.entity.dao.Hub;
import com.yosua.homie.entity.dao.User;
import com.yosua.homie.libraries.exception.BusinessLogicException;
import com.yosua.homie.rest.web.model.request.ACRequest;
import com.yosua.homie.rest.web.model.response.ACResponse;
import com.yosua.homie.rest.web.model.response.ACResponseBuilder;
import com.yosua.homie.service.api.ACService;
import org.apache.commons.lang3.Validate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class ACServiceImpl implements ACService {

    private static final Logger LOGGER = LoggerFactory.getLogger(ACServiceImpl.class);

    @Autowired
    UserRepository userRepository;

    @Autowired
    ACRepository acRepository;

    @Override
    public AC addAC(ACRequest acRequest){
        Validate.notNull(acRequest,"AC Request to be added is required");
        AC newAC;
        User user = userRepository.findUserByHubsIpAddress(acRequest.getHubIP());
        if(Objects.isNull(user))
        {
            throw new BusinessLogicException(ResponseCode.DATA_NOT_EXIST.getCode(),
                    "Hub does not exist!");
        }
        AC existingACWithSameName = acRepository.findACByNameAndHubIP(acRequest.getName(), acRequest.getHubIP());
        if(!Objects.isNull(existingACWithSameName)) {
            throw new BusinessLogicException(ResponseCode.DUPLICATE_DATA.getCode(),
                    "AC with the same name already exist in your hub!");
        }
        newAC = new ACBuilder()
                .withHubIP(acRequest.getHubIP())
                .withName(acRequest.getName())
                .withStatus(acRequest.getStatus())
                .withTemperature(acRequest.getTemperature())
                .build();
        try{
            return acRepository.save(newAC);
        } catch (Exception e) {
            throw new BusinessLogicException(ResponseCode.SYSTEM_ERROR.getCode(),
                    ResponseCode.SYSTEM_ERROR.getMessage());
        }
    }

    @Override
    public List<AC> getAllUsersAC(String userID){
        Validate.notNull(userID,"UserID is required");
        User user = userRepository.findUserById(userID);
        if(Objects.isNull(user))
        {
            throw new BusinessLogicException(ResponseCode.DATA_NOT_EXIST.getCode(),
                    "User does not exist!");
        }
        List<AC> ACs = new ArrayList<>();
        List<Hub> userHubs = user.getHubs();
        List<String> hubsIP = new ArrayList<>();
        if(!Objects.isNull(userHubs) && !userHubs.isEmpty()){
            for(Hub hubs: userHubs)
            {
                hubsIP.add(hubs.getIpAddress());
            }
        }
        LOGGER.info(hubsIP.toString());
        if(!hubsIP.isEmpty()){
            for(String IPs: hubsIP)
            {
                ACs.addAll(acRepository.findACSByHubIP(IPs));
            }
        }
        LOGGER.info("helloow");
        LOGGER.info(ACs.toString());
        return ACs;
    }

    @Override
    public ACResponse toACResponse(AC ac){
        Validate.notNull(ac,"AC is required");
        return new ACResponseBuilder()
                .withHubIP(ac.getHubIP())
                .withName(ac.getName())
                .withStatus(ac.getStatus())
                .withTemperature(ac.getTemperature())
                .build();
    }

    @Override
    public List<ACResponse> toACResponse(List<AC> ACList){
        Validate.notNull(ACList, "AC List is required");
        List<ACResponse> acResponses= new ArrayList<>();
        for(AC ACs: ACList){
            acResponses.add(new ACResponseBuilder()
                                .withHubIP(ACs.getHubIP())
                                .withName(ACs.getName())
                                .withStatus(ACs.getStatus())
                                .withTemperature(ACs.getTemperature())
                                .build());
        }
        return acResponses;
    }
}
