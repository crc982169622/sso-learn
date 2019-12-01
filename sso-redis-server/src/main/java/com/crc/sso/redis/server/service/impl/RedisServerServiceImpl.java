package com.crc.sso.redis.server.service.impl;

import com.crc.sso.redis.server.service.RedisServerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

/**
 * @author: chenrencun
 * @version：
 * @date: 2019/12/1 17:32
 **/
@Service
public class RedisServerServiceImpl implements RedisServerService {

    @Autowired
    private RedisTemplate redisTemplate;

    @Override
    public void put(String key, Object value, long seconds) {
        redisTemplate.opsForValue().set(key, value, seconds, TimeUnit.SECONDS);
    }

    @Override
    public Object get(String key) {
        return redisTemplate.opsForValue().get(key);
    }
}
