package com.crc.sso.client.service.consumer;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author: chenrencun
 * @version：
 * @date: 2019/12/1 17:41
 **/
@FeignClient(value = "sso-redis-server",url = "http://localhost:8082/", fallback = RedisServiceFallback.class)
public interface RedisCacheService {

    @RequestMapping("put")
    public void put(@RequestParam(value = "key") String key,
                    @RequestParam(value = "value") String value,
                    @RequestParam(value = "seconds") long seconds);

    @RequestMapping("get")
    public String get(@RequestParam(value = "key") String key);

}
