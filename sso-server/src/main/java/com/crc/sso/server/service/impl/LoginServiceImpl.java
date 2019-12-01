package com.crc.sso.server.service.impl;

import com.crc.sso.common.util.MapperUtils;
import com.crc.sso.server.domain.User;
import com.crc.sso.server.service.LoginService;
import com.crc.sso.server.service.consumer.RedisCacheService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Author: chenrencun
 * @Date: 2019/11/25 17:56
 * @Description: 描述
 */
@Service
public class LoginServiceImpl implements LoginService {

    @Autowired
    private RedisCacheService redisCacheService;

    @Override
    public User login(String userName, String password) {
        //从缓存中获取登录用户的数据
        String json = (String) redisCacheService.get(userName);
        User user = null;
        //如果缓存中没有数据,从数据库取数据
        if (json == null) {
            if (userName.equals("admin") && password.equals("123456")) {
                user = new User();
                user.setUserName("admin");
                user.setPassword("123456");
                //登录成功，刷新缓存
                try {
                    redisCacheService.put(userName, MapperUtils.obj2json(user), 60 * 60 * 24);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return user;
            }else {
                return null;
            }
        }
        //如果缓存中有数据
        else {
            try {
                user = MapperUtils.json2pojo(json, User.class);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return user;
    }
}
