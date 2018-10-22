package com.yosua.homie.service.impl;

import com.yosua.homie.dao.GasSensorRepository;
import com.yosua.homie.dao.UserRepository;
import com.yosua.homie.entity.constant.ApiPath;
import com.yosua.homie.entity.constant.enums.ResponseCode;
import com.yosua.homie.entity.dao.GasSensor;
import com.yosua.homie.entity.dao.GasSensorBuilder;
import com.yosua.homie.entity.dao.Hub;
import com.yosua.homie.entity.dao.User;
import com.yosua.homie.libraries.exception.BusinessLogicException;
import com.yosua.homie.rest.web.model.request.GasSensorRequest;
import com.yosua.homie.rest.web.model.response.FlaskBaseResponse;
import com.yosua.homie.rest.web.model.response.GasSensorResponse;
import com.yosua.homie.rest.web.model.response.GasSensorResponseBuilder;
import com.yosua.homie.service.api.GasSensorService;
import org.apache.commons.lang3.Validate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class GasSensorServiceImpl implements GasSensorService {
    private static final Logger LOGGER = LoggerFactory.getLogger(GasSensorServiceImpl.class);

    @Value("${homie.firebase.server.key}")
    private String fireBaseServerKey;

    @Autowired
    UserRepository userRepository;

    @Autowired
    GasSensorRepository gasSensorRepository;

    @Override
    public GasSensor addGasSensor(GasSensorRequest gasSensorRequest)
    {
        Validate.notNull(gasSensorRequest,"Gas sensor Request to be added is required");
        GasSensor newGasSensor;
        User user = userRepository.findUserByHubsURL(gasSensorRequest.getHubURL());
        if(Objects.isNull(user))
        {
            throw new BusinessLogicException(ResponseCode.DATA_NOT_EXIST.getCode(),
                    "Hub does not exist!");
        }
        GasSensor existingGasSensorWithSameName = gasSensorRepository.findGasSensorByNameAndHubURL(gasSensorRequest.getName(), gasSensorRequest.getHubURL());
        if(!Objects.isNull(existingGasSensorWithSameName)){
            throw new BusinessLogicException(ResponseCode.DUPLICATE_DATA.getCode(),
                    "Gas sensor with the same name already exist in your hub!");
        }
        newGasSensor = new GasSensorBuilder()
                .withHubURL(gasSensorRequest.getHubURL())
                .withName(gasSensorRequest.getName())
                .withStatus(gasSensorRequest.getStatus())
                .build();
        gasSensorRepository.save(newGasSensor);
        try{
            return gasSensorRepository.save(newGasSensor);
        } catch (Exception e){
            throw new BusinessLogicException(ResponseCode.SYSTEM_ERROR.getCode(),
                    ResponseCode.SYSTEM_ERROR.getMessage());
        }
    }

    @Override
    public GasSensorResponse toGasSensorResponse(GasSensor gasSensor){
        Validate.notNull(gasSensor,"Gas Sensor is required");
        return new GasSensorResponseBuilder()
                .withHubURL(gasSensor.getHubURL())
                .withName(gasSensor.getName())
                .withStatus(gasSensor.getStatus())
                .build();
    }

    @Override
    public List<GasSensor> getAllUsersGasSensor(String userID){
        Validate.notNull(userID, "User ID is required");
        User user = userRepository.findUserById(userID);
        if(Objects.isNull(user))
        {
            throw new BusinessLogicException(ResponseCode.DATA_NOT_EXIST.getCode(), "User does not exist");
        }
        List<GasSensor> GasSensors = new ArrayList<>();
        List<Hub> userHubs = user.getHubs();
        List<String> hubsURL = new ArrayList<>();
        if(!Objects.isNull(userHubs) && !userHubs.isEmpty()){
            for(Hub hubs: userHubs)
            {
                hubsURL.add(hubs.getURL());
            }
        }
        if(!hubsURL.isEmpty()) {
            for (String URLs : hubsURL) {
                GasSensors.addAll(gasSensorRepository.findGasSensorsByHubURL(URLs));
            }
        }
        return GasSensors;
    }

    @Override
    public List<GasSensorResponse> toGasSensorResponse(List<GasSensor> gasSensorList){
        Validate.notNull(gasSensorList, "Gas Sensor list is required");
        List<GasSensorResponse> gasSensorResponses = new ArrayList<>();
        for (GasSensor GasSensors: gasSensorList)
        {
            gasSensorResponses.add(new GasSensorResponseBuilder()
                    .withHubURL(GasSensors.getHubURL())
                    .withName(GasSensors.getName())
                    .withStatus(GasSensors.getStatus())
                    .build());
        }
        return gasSensorResponses;
    }

    @Override
    public FlaskBaseResponse checkGas(String deviceID)
    {
        Validate.notNull(deviceID, "Device ID is required");
        GasSensor gasSensor = gasSensorRepository.findGasSensorById(deviceID);
        if(Objects.isNull(gasSensor))
        {
            throw new BusinessLogicException(ResponseCode.DATA_NOT_EXIST.getCode(),
                    "Gas sensor does not exist");
        }
        final String url = ApiPath.HTTP + gasSensor.getHubURL() + ApiPath.FLASK_CHECK_GAS  + "/" + deviceID + "/";
        LOGGER.info(url);
        RestTemplate restTemplate = new RestTemplate();
        return restTemplate.getForObject(url, FlaskBaseResponse.class);

    }

    @Override
    public String notifyForGas(String userID){
        Validate.notNull(userID, "User ID is required");
        User user = userRepository.findUserById(userID);
        if(Objects.isNull(user)) {
            throw new BusinessLogicException(ResponseCode.DATA_NOT_EXIST.getCode(),
                    "Lamp does not exist!");
        }
        final String url = "https://fcm.googleapis.com/fcm/send";
        LOGGER.info(url);
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");
        headers.add("Authorization", "key=" + fireBaseServerKey);
        String requestJson = "{\n" +
                "    \"notification\": {\n" +
                "        \"title\": \"There is a gas leak in your house\",\n" +
                "        \"body\": \"Contact the police\",\n" +
                "        \"click_action\": \"http://localhost:3000/\",\n" +
                "        \"icon\": \"http://url-to-an-icon/icon.png\"\n" +
                "    },\n" +
                "    \"to\": \"" +  user.getNotificationToken() +"\"\n" +
                "}";
        HttpEntity<String> entity = new HttpEntity<String>(requestJson,headers);
        return restTemplate.postForObject(url, entity, String.class);
    }
}
