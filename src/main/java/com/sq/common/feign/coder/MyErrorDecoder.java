package com.sq.common.feign.coder;

import com.netflix.hystrix.exception.HystrixBadRequestException;
import feign.codec.ErrorDecoder;
import okhttp3.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;

//@Configuration
public class MyErrorDecoder implements ErrorDecoder {

    private static final Logger logger = LoggerFactory.getLogger(MyErrorDecoder.class);

    @Override
    public Exception decode(String methodKey, feign.Response response) {
        logger.error("接口调用异常:{} status:{}, request:{}", methodKey, response.status(), response.request().toString());
        if(response.status() >= 400 && response.status() <= 499){
            return new HystrixBadRequestException(methodKey + response.status());
        }
        return feign.FeignException.errorStatus(methodKey, response);
    }
}