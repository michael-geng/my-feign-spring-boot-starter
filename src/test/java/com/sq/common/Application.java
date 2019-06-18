package com.sq.common;

import com.sq.common.feign.springboot.EnableFeignClients;
import okhttp3.ConnectionPool;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.TimeUnit;


@Configuration
@EnableAutoConfiguration
@EnableFeignClients
public class Application {
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