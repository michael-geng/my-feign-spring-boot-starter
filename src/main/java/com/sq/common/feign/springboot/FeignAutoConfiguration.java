package com.sq.common.feign.springboot;

import com.alibaba.fastjson.parser.ParserConfig;
import com.alibaba.fastjson.serializer.SerializeConfig;
import com.netflix.hystrix.*;
import com.sq.common.feign.coder.FastjsonDecoder;
import com.sq.common.feign.coder.FastjsonEncoder;
import com.sq.common.feign.coder.MyErrorDecoder;
import feign.*;
import feign.codec.Decoder;
import feign.codec.Encoder;
import feign.codec.ErrorDecoder;
import feign.httpclient.ApacheHttpClient;
import feign.hystrix.HystrixFeign;
import feign.hystrix.SetterFactory;
import feign.okhttp.OkHttpClient;
import org.apache.http.client.HttpClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.core.env.Environment;


@Configuration
@ConditionalOnClass(Feign.class)
public class FeignAutoConfiguration {


    @Bean
    @ConditionalOnMissingBean
    public ErrorDecoder MyErrorDecoder(){
        return new MyErrorDecoder();
    }

    @Bean
    @ConditionalOnMissingBean
    public Request.Options getOptions(){
        return new Request.Options(1000, 3000);
    }

    @Bean
    @ConditionalOnMissingBean
    Retryer feignRetryer() {
        return Retryer.NEVER_RETRY;
    }

    @Configuration
    @ConditionalOnClass({ HystrixCommand.class, HystrixFeign.class })
    protected static class HystrixFeignConfiguration {
        @Autowired
        private  Environment env;


        @Bean
        @Scope("prototype")
        @ConditionalOnMissingBean
        @ConditionalOnProperty(name = "feign.hystrix.enabled", matchIfMissing = true)
        public Feign.Builder feignHystrixBuilder() {

            //10秒内请求达到100，错误率达到50%，触发熔断，断路器开启1000ms后尝试关闭
            SetterFactory commandKeyIsRequestLine = (target, method) -> {
                //String groupKey = target.name();
                //String commandKey = method.getAnnotation(RequestLine.class).value();

                String groupKey = target.type().getSimpleName();
                String commandKey = method.getName();
                Integer timeoutInMilliseconds = Integer.valueOf(env.getProperty(groupKey+".timeoutInMilliseconds", "3000"));
                return HystrixCommand.Setter
                        .withGroupKey(HystrixCommandGroupKey.Factory.asKey(groupKey))
                        .andCommandKey(HystrixCommandKey.Factory.asKey(commandKey))
                        .andCommandPropertiesDefaults(HystrixCommandProperties.Setter()
                                .withExecutionTimeoutInMilliseconds(timeoutInMilliseconds)
                                .withCircuitBreakerRequestVolumeThreshold(100)
                                .withCircuitBreakerSleepWindowInMilliseconds(1000)
                                .withCircuitBreakerErrorThresholdPercentage(50))
                        .andThreadPoolKey(HystrixThreadPoolKey.Factory.asKey("Feign-hystrix-pool"))
                        .andThreadPoolPropertiesDefaults(HystrixThreadPoolProperties.Setter().withCoreSize(100));
                        //默认10个线程
            };
            return HystrixFeign.builder().setterFactory(commandKeyIsRequestLine);
        }
    }

    @Bean
    @Scope("prototype")
    @ConditionalOnMissingBean
    public Feign.Builder feignBuilder() {
        return Feign.builder();
    }

    /*@Configuration
    @ConditionalOnClass({ JacksonDecoder.class, JacksonEncoder.class })
    protected static class JacksonFeignConfiguration {
        @Bean
        @ConditionalOnMissingBean
        public Decoder feignDecoder() {
            return new JacksonDecoder();
        }

        @Bean
        @ConditionalOnMissingBean
        public Encoder feignEncoder() {
            return new JacksonEncoder();
        }
    }*/

    @Configuration
    @ConditionalOnClass({ FastjsonDecoder.class, FastjsonEncoder.class })
    protected static class JacksonFeignConfiguration {
        @Bean
        @ConditionalOnMissingBean
        public Decoder feignDecoder() {
            ParserConfig parserConfig = new ParserConfig();

            return new FastjsonDecoder(parserConfig);
        }

        @Bean
        @ConditionalOnMissingBean
        public Encoder feignEncoder() {
            SerializeConfig serializeConfig = new SerializeConfig();
            return new FastjsonEncoder(serializeConfig);
        }
    }


    @Configuration
    @ConditionalOnClass(ApacheHttpClient.class)
    @ConditionalOnProperty(value = "feign.httpclient.enabled", matchIfMissing = false)
    protected static class HttpClientFeignConfiguration {
        @Autowired(required = false)
        private HttpClient httpClient;

        @Bean
        @ConditionalOnMissingBean(Client.class)
        public Client feignClient() {
            if (this.httpClient != null) {
                return new ApacheHttpClient(this.httpClient);
            }
            return new ApacheHttpClient();
        }
    }

    @Configuration
    @ConditionalOnClass(okhttp3.OkHttpClient.class)
    @ConditionalOnProperty(value = "feign.okhttp.enabled", matchIfMissing = true)
    protected static class OkHttpFeignConfiguration {
        @Autowired(required = false)
        private okhttp3.OkHttpClient okHttpClient;

        @Bean
        @ConditionalOnMissingBean(Client.class)
        public Client feignClient() {
            if (this.okHttpClient != null) {
                return new OkHttpClient(this.okHttpClient);
            }
            return new OkHttpClient();
        }
    }

}
