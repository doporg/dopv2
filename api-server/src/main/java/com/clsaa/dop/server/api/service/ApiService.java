package com.clsaa.dop.server.api.service;

import com.clsaa.dop.server.api.module.request.lifeCycle.CreateApiParams;
import com.clsaa.dop.server.api.module.request.lifeCycle.ModifyApiParams;
import com.clsaa.dop.server.api.module.response.ApiDetail;
import com.clsaa.dop.server.api.module.response.ResponseResult;


public interface ApiService {
    ResponseResult<String> createApi(CreateApiParams createApiParams);

    ResponseResult onlineApi(String apiId);

    ResponseResult offlineApi(String apiId);

    ResponseResult deleteApi(String apiId);

    ResponseResult modifyApi(ModifyApiParams modifyApiParams);

    ResponseResult<ApiDetail> getApi(String apiId);

    ResponseResult<ApiDetail[]> getApiList();
}
