package com.gyy.serializer;

import com.alibaba.fastjson.JSON;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.lang.Nullable;
import org.springframework.util.Assert;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

/**
 *  自定义序列化
 */

public class MyStringRedisSerializer implements RedisSerializer<Object> {
    private final Charset charset;
    public static final org.springframework.data.redis.serializer.StringRedisSerializer US_ASCII;
    public static final org.springframework.data.redis.serializer.StringRedisSerializer ISO_8859_1;
    public static final org.springframework.data.redis.serializer.StringRedisSerializer UTF_8;

    public MyStringRedisSerializer() {
        this(StandardCharsets.UTF_8);
    }

    public MyStringRedisSerializer(Charset charset) {
        Assert.notNull(charset, "Charset must not be null!");
        this.charset = charset;
    }



    public String deserialize(@Nullable byte[] bytes) {
        return bytes == null ? null : new String(bytes, this.charset);
    }

    public byte[] serialize(@Nullable Object o) {
        if (o == null){
            return new byte[0];
        }
        if (o instanceof String){
            return o.toString().getBytes(charset);
        }else {
            String str = JSON.toJSONString(o);
            return str.getBytes(charset);
        }
    }

    static {
        US_ASCII = new org.springframework.data.redis.serializer.StringRedisSerializer(StandardCharsets.US_ASCII);
        ISO_8859_1 = new org.springframework.data.redis.serializer.StringRedisSerializer(StandardCharsets.ISO_8859_1);
        UTF_8 = new org.springframework.data.redis.serializer.StringRedisSerializer(StandardCharsets.UTF_8);
    }
}
