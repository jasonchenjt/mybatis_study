package com.didispace;

import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.core.RedisTemplate;

public class RedisConfig extends CachingConfigurerSupport {

    @Bean
    public CacheManager cacheManager(
            @SuppressWarnings("rawtypes") RedisTemplate redisTemplate) {
        RedisCacheManager cacheManager = new RedisCacheManager(redisTemplate);
// cacheManager.setDefaultExpiration(60);//设置缓存保留时间（seconds）
        cacheManager.setUsePrefix(true);
        return cacheManager;
    }
}
