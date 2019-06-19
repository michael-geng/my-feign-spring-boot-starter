---
feign的 springboot 插件
---

#### feign介绍
>Feign是Netflix开发的声明式、模板化的HTTP客户端， Feign可以帮助我们更快捷、优雅地调用HTTP API。

>Spring Cloud中对 feign 进行了封装，可以通过 springmvc 的注解来注解 http api的请求参数，使用上可以说非常的方便。
Spring Cloud Feign具备可插拔的注解支持，支持Feign注解、JAX-RS注解和Spring MVC的注解。
但是需要在 spring cloud 全家桶里才能使用。
> 我们后端项目都是 springboot 提供 http 接口，项目之间有http的接口调用，使用 okhttp、httpclient都比较麻烦，我们希望能脱离 springcloud 环境，
使用注解来加载 feign的client，所以参考了 spring-cloud-feign 的封装，提供了 springboot 的自动注解功能。

>Feign可以使用 OKhttp、HttpClient 作为 Http 的请求

### feign 机制

### 封装 springboot 插件
1. 创建注解@EnableFeignClients、@FeignClient，可以通过注解自动扫描接口
2. 创建FeignClientFactoryBean，在启动时加载
对于http 的接口，可以统一返回 json 格式的数据，使用 fastjson 对返回的文本进行 decoder。
feign 提供了很好的扩展机制，继承Decoder进行解码扩展

### 源码
git地址：