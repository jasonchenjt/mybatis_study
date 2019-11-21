package com.didispace.service;

import com.didispace.Application;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

@Service
public class CacheService {

    @Autowired
    private RedisTemplate redisTemplate;

    @Cacheable(value = "redisCache",key = "#value")
    public String cacheable(String value){
        System.out.println("11111");
        return "cacheable:"+value;
    }

    @CachePut(value = "redisCache",key="#value")
    public String cachePut(String value){
        ValueOperations<String, String> operations = redisTemplate.opsForValue();

        boolean hasKey = redisTemplate.hasKey("redisCache");
        if (hasKey) {
            String city = operations.get(value);

//            LOGGER.info("CityServiceImpl.findCityById() : 从缓存中获取了城市 >> " + city.toString());
           System.out.println("在缓存在获取信息:"+city);
        }

        System.out.println("CachePut---不在缓存中.");
        return "CachePut:"+value;
    }

    @CacheEvict(value = "redisCache",key = "#value")
    public String cacheEvict(String value){
        System.out.println("33333");
        return "cacheEvict:"+value;
    }


}
