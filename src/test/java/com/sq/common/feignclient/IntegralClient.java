package com.sq.common.feignclient;

import com.sq.common.feign.RpcResponseResult;
import com.sq.common.feign.springboot.FeignClient;
import feign.*;

import java.util.Map;

@FeignClient("http://dev-inside-driver-integral.*.com")
public interface IntegralClient {
    @RequestLine("GET integral/cityConfig?driverId={driverId}")
    RpcResponseResult<DriverLevelCityConfig> getCityConfig(@Param("driverId") String driverId);


    /**
     * 传递对象
     * @param bean
     * @return
     */
    @RequestLine("POST integral/detailMsg")
    RpcResponseResult<String> detailMsgByBean(@QueryMap DriverRequestBean bean);

    /**
     * 传递 map
     * @param param
     * @return
     */
    @RequestLine("POST integral/detailMsg")
    RpcResponseResult<String> detailMsgByMap(@QueryMap Map<String, Object> param);

    /**
     * 通过参数指定 post 内容
     */
    @RequestLine("POST integral/detailMsg?driverId={driverId}&createTime={createTime}")
    RpcResponseResult<String> detailMsg(@Param("driverId") String driverId, @Param("createTime") String createTime);


}
