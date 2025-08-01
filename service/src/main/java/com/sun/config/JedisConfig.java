package com.sun.config;

import com.bailian.scloud.common.util.redis.JedisUtils;
import com.bailian.scloud.common.util.redis.RedisConfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;


@Configuration
public class JedisConfig {

    @Value("${spring.redis.host:127.0.0.1}")
    private String host;
    @Value("${spring.redis.port:6379}")
    private int port;
    @Value("${spring.redis.password:}")
    private String password;
    @Value("${spring.redis.jedis.pool.max-idle:300}")
    private int maxIdle;
    @Value("${spring.redis.jedis.pool.max-active:200}")
    private int maxTotal;
    @Value("${spring.redis.jedis.pool.maxWaitMillis:500}")
    private long maxWaitMillis;

    @Value("${spring.redis.database:13}")
    private int database;
    @Value("${spring.redis.timeout:1000}")
    private int timeout;



    @Bean(name = "jedisUtils")
    public JedisUtils jedisUtils() {
        RedisConfig redisConfig = new RedisConfig();
        redisConfig.setHost(host);
        redisConfig.setPort(port);
        redisConfig.setPassword(password);
        redisConfig.setTimeout(timeout);
        redisConfig.setDatabase(database);
        redisConfig.setMaxTotal(maxTotal);
        redisConfig.setMaxIdle(maxIdle);
        redisConfig.setMaxWaitMillis(maxWaitMillis);
        return new JedisUtils(redisConfig);
    }

    @Bean
    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory factory) {
        RedisTemplate<String, Object> template = new RedisTemplate<>();
        template.setConnectionFactory(factory);
        template.setKeySerializer(new StringRedisSerializer());
        template.setValueSerializer(new GenericJackson2JsonRedisSerializer());
        return template;
    }

}
