package com.yosua.homie.service.api;

import com.yosua.homie.entity.dao.Hub;
import com.yosua.homie.entity.dao.User;
import com.yosua.homie.rest.web.model.request.HubsRequest;

import java.util.List;

public interface HubService {
    User addHubs(HubsRequest hubsRequest, String userID);
    User editHubs(String userID, String URL, String updatedPhysicalAddress);
    List<Hub> toHubs(HubsRequest hubsRequest);
}
