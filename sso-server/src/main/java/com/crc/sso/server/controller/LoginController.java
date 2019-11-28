package com.crc.sso.server.controller;

import com.crc.sso.common.util.CookieUtils;
import com.crc.sso.common.util.JsonUtils;
import com.crc.sso.common.util.MapperUtils;
import com.crc.sso.server.domain.User;
import com.crc.sso.server.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

/**
 * @Author: chenrencun
 * @Date: 2019/11/25 18:14
 * @Description: 描述
 */
@Controller
public class LoginController {

    @Autowired
    private LoginService loginService;

    @Autowired
    private RedisTemplate redisTemplate;

    /**
     * 跳转登录页
     * @param request
     * @param url
     * @return
     */
    @GetMapping("/login")
    public String login(HttpServletRequest request,
                        @RequestParam(required = false) String url) {
        String token = CookieUtils.getCookieValue(request, "token");
        //token不为空可能已登录,从redis获取账号
        if (token != null && token.trim().length() != 0) {
            String userName = (String)redisTemplate.opsForValue().get(token);
            //如果账号不为空，从redis获取该账号的个人信息
            if (userName != null && userName.trim().length() != 0) {
                String json = (String) redisTemplate.opsForValue().get(userName);
                if (json != null) {
                    User user = null;
                    try {
                        user = MapperUtils.json2pojo(json, User.class);
                        if (user != null) {
                            if (url != null && url.trim().length() != 0) {
                                return "redirect:" + url;
                            }
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    //将登录信息传到登录页
                    request.setAttribute("user", user);
                }
            }
        }
        return "login";
    }

    @PostMapping("/login")
    public String login(String userName,
                        String password,
                        @RequestParam(required = false) String url,
                        HttpServletRequest request,
                        HttpServletResponse response) {
        User user = loginService.login(userName, password);
        if (user != null) {
            String token = UUID.randomUUID().toString();
            //将token放入缓存
            redisTemplate.opsForValue().set(token, userName, 60*60*24);
            CookieUtils.setCookie(request, response,"token", token, 60*60*24);
            if (url != null)
                return "redirect:" + url;
        }
        return "redirect:/login";
    }
}
