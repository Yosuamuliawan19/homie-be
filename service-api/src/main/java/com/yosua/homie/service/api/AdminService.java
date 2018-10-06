package com.yosua.homie.service.api;

import com.yosua.homie.entity.dao.User;
import com.yosua.homie.rest.web.model.request.HubsRequest;


public interface AdminService {
    User addHubs(HubsRequest hubsRequest, String userID);
}

