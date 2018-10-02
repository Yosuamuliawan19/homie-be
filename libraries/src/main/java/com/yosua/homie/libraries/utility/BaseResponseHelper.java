package com.yosua.homie.libraries.utility;

import com.yosua.homie.rest.web.model.response.BaseResponse;
import com.yosua.homie.rest.web.model.response.BaseResponseBuilder;

import java.util.Date;
import java.util.List;

public class BaseResponseHelper {

  private BaseResponseHelper(){}

  public static <T> BaseResponse<T> constructResponse(String code, String message, List<String> errors, T data) {
    return (new BaseResponseBuilder()).withCode(code).withMessage(message).withErrors(errors).withServerTime(new Date()).withData(data).build();
  }
}