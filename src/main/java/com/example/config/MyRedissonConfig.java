package com.example.config;

import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.client.codec.Codec;
import org.redisson.client.codec.StringCodec;
import org.redisson.codec.JsonJacksonCodec;
import org.redisson.config.Config;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import java.nio.charset.Charset;


/**
 * Created with IntelliJ IDEA.
 *
 * @Author: Leo
 * @Date: 2023/07/04/上午10:27
 * @Description:
 */
@Configuration
@Component
public class MyRedissonConfig {
    private static final Logger LOGGER = LoggerFactory.getLogger(Redisson.class);

    @Bean
    public RedissonClient redissonClient() {
        Config config = new Config();
        config.useSingleServer().setAddress("redis://localhost:6379");

        Codec codec = new JsonJacksonCodec();

        config.setCodec(codec);
        RedissonClient redisson = Redisson.create(config);
        return redisson;
    }
}
