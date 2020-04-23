package com.clsaa.dop.server.api.service;

import com.clsaa.dop.server.api.module.vo.request.lifeCycle.CreateApiParams;
import com.clsaa.dop.server.api.module.vo.request.lifeCycle.ModifyApiParams;
import com.clsaa.dop.server.api.module.vo.response.ApiDetail;
import com.clsaa.dop.server.api.module.vo.response.ApiList;
import com.clsaa.dop.server.api.module.vo.response.ResponseResult;


public interface ApiService {
    ResponseResult<String> createApi(CreateApiParams createApiParams);

    ResponseResult onlineApi(String apiId);

    ResponseResult offlineApi(String apiId);

    ResponseResult deleteApi(String apiId);

    ResponseResult modifyApi(String apiId,ModifyApiParams modifyApiParams);

    ResponseResult<ApiDetail> getApi(String apiId);

    ResponseResult<ApiList> getApiList(int pageNo, int pageSize, String apiType);
}
