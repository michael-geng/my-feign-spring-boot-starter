package com.sq.common.feignclient;

import com.alibaba.fastjson.JSON;
import com.sq.common.feign.RpcResponseResult;
import com.sq.common.feign.springboot.EnableFeignClients;
import okhttp3.Call;
import okhttp3.ConnectionPool;
import okhttp3.Request;
import okhttp3.Response;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.IOException;
import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(
        classes = FeignClientTests.Application.class,
        webEnvironment = SpringBootTest.WebEnvironment.NONE,
        value = "")
@DirtiesContext
public class FeignClientTests {

    @Configuration
    @EnableAutoConfiguration
    @EnableFeignClients
    public static class Application {
        public static void main(String[] args) {
            SpringApplication.run(Application.class, args);
        }

        @Bean
        public okhttp3.OkHttpClient getOkHttpClient(){
            ConnectionPool connectionPool = new ConnectionPool(100, 3L, TimeUnit.MINUTES);
            okhttp3.OkHttpClient client = new okhttp3.OkHttpClient.Builder().connectionPool(connectionPool)
                    .connectTimeout(1, TimeUnit.SECONDS)
                    .readTimeout(1, TimeUnit.SECONDS)
                    .build();
            return client;
        }

    }

    @Autowired
    private GitHub github;

    @Autowired
    private okhttp3.OkHttpClient okHttpClient;

    @Autowired
    private IntegralClient integralClient;

    @Test
    public void testOkHttpClient(){
        Request.Builder builder = new Request.Builder().url("https://api.github.com/repos/xiegang/spring-boot-starter-feign/contributors");
        Call call = okHttpClient.newCall(builder.build());
        try {
            Response response = call.execute();
            System.out.println(response.body().string());
        } catch (IOException e) {
            e.printStackTrace();
            if(e instanceof SocketTimeoutException){//判断超时异常

            }
            if(e instanceof ConnectException){//判断连接异常，我这里是报Failed to connect to

            }

        }
    }
    @Test
    public void testGithub() {
        long start = System.currentTimeMillis();
        github.contributors("xiegang", "spring-boot-starter-feign").forEach(contributor -> {
            System.out.println(contributor.login + " ---(" + contributor.contributions + ")");
        });
        System.out.println(System.currentTimeMillis() - start);
    }

    @Test
    public void testIntegralCityConfig(){
        System.out.println("test started...........");
        for (int i=1; i<2; i++){
            RpcResponseResult cityConfig = this.integralClient.getCityConfig("100026111");

            System.out.println(JSON.toJSONString(cityConfig));
        }
    }

    @Test
    public void testIntegral(){
        long start = System.currentTimeMillis();

        System.out.println("test started...........");
        Map<String, Object> params = new HashMap<>();
        params.put("driverId", "100026111");
        params.put("createTime", "2019-02-22");

        DriverRequestBean bean = new DriverRequestBean("100026111", "2019-02-22");
        for (int i=0; i<1; i++){
            //RpcResponseResult<String> cityConfig = this.integralClient.detailMsgByBean(bean);
            //RpcResponseResult<String> cityConfig = this.integralClient.detailMsg("100026111", "2019-02-22");

            RpcResponseResult<String> cityConfig = this.integralClient.detailMsgByBean(bean);
            //RpcResponseResult<String> cityConfig = this.integralClient.detailMsgByMap(params);

            System.out.println(JSON.toJSONString(cityConfig));
        }

        System.out.println(System.currentTimeMillis() - start);
    }
}