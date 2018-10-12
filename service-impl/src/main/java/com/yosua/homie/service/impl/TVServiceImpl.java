package com.yosua.homie.service.impl;

import com.yosua.homie.dao.TVRepository;
import com.yosua.homie.dao.UserRepository;
import com.yosua.homie.entity.constant.ApiPath;
import com.yosua.homie.entity.constant.enums.ResponseCode;
import com.yosua.homie.entity.dao.*;
import com.yosua.homie.libraries.exception.BusinessLogicException;
import com.yosua.homie.rest.web.model.request.TVRequest;
import com.yosua.homie.rest.web.model.response.ACResponse;
import com.yosua.homie.rest.web.model.response.FlaskBaseResponse;
import com.yosua.homie.rest.web.model.response.TVResponse;
import com.yosua.homie.rest.web.model.response.TVResponseBuilder;
import com.yosua.homie.service.api.TVService;
import org.apache.commons.lang3.Validate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class TVServiceImpl implements TVService {

    private static final Logger LOGGER = LoggerFactory.getLogger(TVServiceImpl.class);

    @Autowired
    UserRepository userRepository;

    @Autowired
    TVRepository tvRepository;

    @Override
    public TV addTV(TVRequest tvRequest){
        Validate.notNull(tvRequest,"TV Request to be added is required");
        TV newTV;
        User user = userRepository.findUserByHubsURL(tvRequest.getHubURL());
        if(Objects.isNull(user))
        {
            throw new BusinessLogicException(ResponseCode.DATA_NOT_EXIST.getCode(),
                    "Hub does not exist!");
        }
        TV existingTVithSameName = tvRepository.findTVByNameAndHubURL(tvRequest.getName(), tvRequest.getHubURL());
        if(!Objects.isNull(existingTVithSameName)) {
            throw new BusinessLogicException(ResponseCode.DUPLICATE_DATA.getCode(),
                    "TV with the same name already exist in your hub!");
        }
        newTV = new TVBuilder()
                .withHubURL(tvRequest.getHubURL())
                .withName(tvRequest.getName())
                .withStatus(tvRequest.getStatus())
                .withChannelNumber(tvRequest.getChannelNumber())
                .withVolume(tvRequest.getVolume())
                .withMuted(tvRequest.getMuted())
                .build();
        try{
            return tvRepository.save(newTV);
        } catch (Exception e) {
            throw new BusinessLogicException(ResponseCode.SYSTEM_ERROR.getCode(),
                    ResponseCode.SYSTEM_ERROR.getMessage());
        }
    }

    @Override
    public List<TV> getAllUsersTV(String userID){
        Validate.notNull(userID,"UserID is required");
        User user = userRepository.findUserById(userID);
        if(Objects.isNull(user))
        {
            throw new BusinessLogicException(ResponseCode.DATA_NOT_EXIST.getCode(),
                    "User does not exist!");
        }
        List<TV> TVs = new ArrayList<>();
        List<Hub> userHubs = user.getHubs();
        List<String> hubsURL = new ArrayList<>();
        if(!Objects.isNull(userHubs) && !userHubs.isEmpty()){
            for(Hub hubs: userHubs)
            {
                hubsURL.add(hubs.getURL());
            }
        }
        if(!hubsURL.isEmpty()){
            for(String URLs: hubsURL)
            {
                TVs.addAll(tvRepository.findTVByHubURL(URLs));
            }
        }
        return TVs;
    }

    @Override
    public TVResponse toTVResponse(TV tv){
        Validate.notNull(tv,"TV is required");
        return new TVResponseBuilder()
                .withHubURL(tv.getHubURL())
                .withName(tv.getName())
                .withStatus(tv.getStatus())
                .withChannelNumber(tv.getChannelNumber())
                .withVolume(tv.getVolume())
                .withMuted(tv.getMuted())
                .build();
    }

    @Override
    public List<TVResponse> toTVResponse(List<TV> TVList){
        Validate.notNull(TVList, "TV List is required");
        List<TVResponse> tvResponses= new ArrayList<>();
        for(TV TVs: TVList){
            tvResponses.add(new TVResponseBuilder()
                    .withHubURL(TVs.getHubURL())
                    .withName(TVs.getName())
                    .withStatus(TVs.getStatus())
                    .withChannelNumber(TVs.getChannelNumber())
                    .withVolume(TVs.getVolume())
                    .withMuted(TVs.getMuted())
                    .build());
        }
        return tvResponses;
    }
    @Override
    public FlaskBaseResponse turnOnTV(String deviceID){
        Validate.notNull(deviceID, "Device ID is required");
        TV tv = tvRepository.findTVById(deviceID);
        if(Objects.isNull(tv)) {
            throw new BusinessLogicException(ResponseCode.DATA_NOT_EXIST.getCode(),
                    "TV does not exist!");
        }
        final String url = ApiPath.HTTP + tv.getHubURL() + ApiPath.FLASK_TURN_ON_TV + deviceID + "/";
        LOGGER.info(url);
        RestTemplate restTemplate = new RestTemplate();
        return restTemplate.getForObject(url, FlaskBaseResponse.class);
    }
    @Override
    public FlaskBaseResponse turnOffTV(String deviceID){
        Validate.notNull(deviceID, "Device ID is required");
        TV tv = tvRepository.findTVById(deviceID);
        if(Objects.isNull(tv)) {
            throw new BusinessLogicException(ResponseCode.DATA_NOT_EXIST.getCode(),
                    "TV does not exist!");
        }
        final String url = ApiPath.HTTP + tv.getHubURL() + ApiPath.FLASK_TURN_OFF_TV + deviceID + "/";
        LOGGER.info(url);
        RestTemplate restTemplate = new RestTemplate();
        return restTemplate.getForObject(url, FlaskBaseResponse.class);
    }
    @Override
    public FlaskBaseResponse volumeUpTV(String deviceID){
        Validate.notNull(deviceID, "Device ID is required");
        TV tv = tvRepository.findTVById(deviceID);
        if(Objects.isNull(tv)) {
            throw new BusinessLogicException(ResponseCode.DATA_NOT_EXIST.getCode(),
                    "TV does not exist!");
        }
        final String url = ApiPath.HTTP + tv.getHubURL() + ApiPath.FLASK_VOLUME_UP_TV+ deviceID + "/";
        LOGGER.info(url);
        RestTemplate restTemplate = new RestTemplate();
        return restTemplate.getForObject(url, FlaskBaseResponse.class);
    }
    @Override
    public FlaskBaseResponse volumeDownTV(String deviceID){
        Validate.notNull(deviceID, "Device ID is required");
        TV tv = tvRepository.findTVById(deviceID);
        if(Objects.isNull(tv)) {
            throw new BusinessLogicException(ResponseCode.DATA_NOT_EXIST.getCode(),
                    "TV does not exist!");
        }
        final String url = ApiPath.HTTP + tv.getHubURL() + ApiPath.FLASK_VOLUME_DOWN_TV+ deviceID + "/";
        LOGGER.info(url);
        RestTemplate restTemplate = new RestTemplate();
        return restTemplate.getForObject(url, FlaskBaseResponse.class);
    }
    @Override
    public FlaskBaseResponse programUpTV(String deviceID){
        Validate.notNull(deviceID, "Device ID is required");
        TV tv = tvRepository.findTVById(deviceID);
        if(Objects.isNull(tv)) {
            throw new BusinessLogicException(ResponseCode.DATA_NOT_EXIST.getCode(),
                    "TV does not exist!");
        }
        final String url = ApiPath.HTTP + tv.getHubURL() + ApiPath.FLASK_PROGRAM_UP_TV+ deviceID + "/";
        LOGGER.info(url);
        RestTemplate restTemplate = new RestTemplate();
        return restTemplate.getForObject(url, FlaskBaseResponse.class);
    }
    @Override
    public FlaskBaseResponse programDownTV(String deviceID){
        Validate.notNull(deviceID, "Device ID is required");
        TV tv = tvRepository.findTVById(deviceID);
        if(Objects.isNull(tv)) {
            throw new BusinessLogicException(ResponseCode.DATA_NOT_EXIST.getCode(),
                    "TV does not exist!");
        }
        final String url = ApiPath.HTTP + tv.getHubURL() + ApiPath.FLASK_PROGRAM_DOWN_TV+ deviceID + "/";
        LOGGER.info(url);
        RestTemplate restTemplate = new RestTemplate();
        return restTemplate.getForObject(url, FlaskBaseResponse.class);
    }


}
