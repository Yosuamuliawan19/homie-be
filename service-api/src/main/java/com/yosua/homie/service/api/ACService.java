package com.yosua.homie.service.api;

import com.yosua.homie.entity.dao.AC;
import com.yosua.homie.entity.dao.User;
import com.yosua.homie.rest.web.model.request.ACRequest;
import com.yosua.homie.rest.web.model.response.ACResponse;

import java.util.List;

public interface ACService {
    AC addAC(ACRequest acRequest);
    ACResponse toACResponse(AC ac);
    List<AC> getAllUsersAC(String userID);
    List<ACResponse> toACResponse(List<AC> ACList);
}
