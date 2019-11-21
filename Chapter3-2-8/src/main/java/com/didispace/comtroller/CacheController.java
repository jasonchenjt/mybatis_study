package com.didispace.comtroller;

import com.didispace.service.CacheService;
import com.didispace.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CacheController {
    @Autowired
    CacheService cacheService;

    @Autowired
    UserService userService;

    @Autowired
    StringRedisTemplate redisTemplate;

    @RequestMapping("/CachePut/{value}")
    public String cachePut(@PathVariable String value) {
        return cacheService.cachePut(value);
    }

    @RequestMapping("/Cacheable/{value}")
    public String cacheable(@PathVariable String value) {
        return cacheService.cacheable(value);
    }

    @RequestMapping("/CacheEvict/{value}")
    public String cacheEvict(@PathVariable String value) {
        return cacheService.cacheEvict(value);
    }

    @RequestMapping("/test")
    public void test() {
         userService.finduserbyname("Jason");
    }

    @RequestMapping("/get/{key}")
    public void get(@PathVariable String key) {
        String name = redisTemplate.opsForValue().get(key);
        System.out.println("name:"+name);
    }

}
