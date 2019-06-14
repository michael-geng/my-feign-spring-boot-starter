package com.sq.common.feign.springboot;

import feign.*;
import feign.codec.Decoder;
import feign.codec.Encoder;
import feign.codec.ErrorDecoder;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactoryUtils;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.util.Assert;

import java.util.Map;

class FeignClientFactoryBean implements FactoryBean<Object>, InitializingBean, ApplicationContextAware {

    private Class<?> type;

    private String url;

    private boolean decode404;

    private ApplicationContext applicationContext;

    @Override
    public void afterPropertiesSet() throws Exception {
        Assert.hasText(this.url, "url must be set");
    }

    @Override
    public void setApplicationContext(ApplicationContext context) throws BeansException {
        this.applicationContext = context;
    }

    protected Feign.Builder feign() {
        Feign.Builder builder = get(Feign.Builder.class)
                .encoder(get(Encoder.class))
                .decoder(get(Decoder.class));

        // optional values
        Client client = getOptional(Client.class);
        if (client != null) {
            builder.client(client);
        }
        Contract contract = getOptional(Contract.class);
        if (contract != null) {
            builder.contract(contract);
        }
        Logger.Level level = getOptional(Logger.Level.class);
        if (level != null) {
            builder.logLevel(level);
        }
        Retryer retryer = getOptional(Retryer.class);
        if (retryer != null) {
            builder.retryer(retryer);
        }
        ErrorDecoder errorDecoder = getOptional(ErrorDecoder.class);
        if (errorDecoder != null) {
            builder.errorDecoder(errorDecoder);
        }
        Request.Options options = getOptional(Request.Options.class);
        if (options != null) {
            builder.options(options);
        }

        Map<String, RequestInterceptor> requestInterceptors = getOptionals(RequestInterceptor.class);
        if (requestInterceptors != null) {
            builder.requestInterceptors(requestInterceptors.values());
        }

        if (decode404) {
            builder.decode404();
        }

        return builder;
    }

    protected <T> T get(Class<T> type) {
        if (BeanFactoryUtils.beanNamesForTypeIncludingAncestors(applicationContext, type).length > 0) {
            return BeanFactoryUtils.beanOfTypeIncludingAncestors(applicationContext, type);
        } else {
            throw new IllegalStateException("No bean found of type " + type);
        }
    }

    protected <T> T getOptional(Class<T> type) {
        if (BeanFactoryUtils.beanNamesForTypeIncludingAncestors(applicationContext, type).length > 0) {
            return BeanFactoryUtils.beanOfTypeIncludingAncestors(applicationContext, type);
        }
        return null;
    }

    protected <T> Map<String, T> getOptionals(Class<T> type) {
        if (BeanFactoryUtils.beanNamesForTypeIncludingAncestors(applicationContext, type).length > 0) {
            return BeanFactoryUtils.beansOfTypeIncludingAncestors(applicationContext, type);
        }
        return null;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Class<?> getType() {
        return type;
    }

    public void setType(Class<?> type) {
        this.type = type;
    }

    public void setDecode404(boolean decode404) {
        this.decode404 = decode404;
    }

    public boolean isDecode404() {
        return decode404;
    }

    public ApplicationContext getApplicationContext() {
        return applicationContext;
    }


    @Override
    public Object getObject() throws Exception {
        return feign().target(type, url);
    }

    @Override
    public Class<?> getObjectType() {
        return this.type;
    }

    @Override
    public boolean isSingleton() {
        return true;
    }

}
