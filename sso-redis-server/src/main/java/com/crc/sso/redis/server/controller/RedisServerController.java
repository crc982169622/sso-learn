package com.crc.sso.redis.server.controller;

import com.crc.sso.redis.server.service.RedisServerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author: chenrencun
 * @version：
 * @date: 2019/12/1 17:33
 **/
@RestController
public class RedisServerController {

    @Autowired
    private RedisServerService redisServerService;

    @RequestMapping("/get")
    public String get(String key) {
        Object o = redisServerService.get(key);
        if (o != null) {
            String value = String.valueOf(o);
            return value;
        }
        return null;
    }

    @RequestMapping("/put")
    public String put(String key, String value, long seconds) {
        redisServerService.put(key, value, seconds);
        return "ok";
    }
}
