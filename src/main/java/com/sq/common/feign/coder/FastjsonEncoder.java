package com.sq.common.feign.coder;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializeConfig;
import feign.RequestTemplate;
import feign.codec.EncodeException;
import feign.codec.Encoder;

import java.lang.reflect.Type;

/**
 */
public class FastjsonEncoder implements Encoder {
    private SerializeConfig config = null;

    public FastjsonEncoder() {
        this(null);
    }

    public FastjsonEncoder(SerializeConfig config) {
        if (null != config) {
            this.config = config;
        } else {
            this.config = SerializeConfig.getGlobalInstance();
        }
    }

    /*
     * (non-Javadoc)
     *
     * @see feign.codec.Encoder#encode(java.lang.Object, java.lang.reflect.Type, feign.RequestTemplate)
     */
    @Override
    public void encode(Object obj, Type type, RequestTemplate template) throws EncodeException {
        template.body(JSON.toJSONString(obj,config));
    }

}