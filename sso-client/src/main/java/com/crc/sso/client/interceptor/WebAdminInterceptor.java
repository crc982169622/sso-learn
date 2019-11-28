package com.crc.sso.client.interceptor;

import com.crc.sso.client.domain.User;
import com.crc.sso.client.util.CookieUtils;
import com.crc.sso.common.util.MapperUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * @Author: chenrencun
 * @Date: 2019/11/26 9:24
 * @Description: 描述
 */
public class WebAdminInterceptor implements HandlerInterceptor {

    @Autowired
    private RedisTemplate redisTemplate;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws IOException {
        String token = CookieUtils.getCookieValue(request, "token");
        //token为空，一定没有登录
        if (token == null || token.isEmpty()) {
            response.sendRedirect("http://127.0.0.1:8080/login?url=http://127.0.0.1:8081/login");
            return false;
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws IOException {

        HttpSession session = request.getSession();
        User user = (User)session.getAttribute("user");
        //已登陆状态
        if (user != null) {
            if (modelAndView != null) {
                modelAndView.addObject("user", user);

            }
        }
        //未登录状态
        else {
            String token = CookieUtils.getCookieValue(request, "token");
            if (token != null && !token.isEmpty()) {
                String userName = (String)redisTemplate.opsForValue().get(token);
                if (userName != null && !userName.isEmpty()) {
                    String json = (String)redisTemplate.opsForValue().get(userName);
                    if (json != null) {
                        try {
                            user = MapperUtils.json2pojo(json, User.class);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        //已登录状态，创建局部会话
                        if (modelAndView != null) {
                            modelAndView.addObject("user", user);
                        }
                        request.getSession().setAttribute("user", user);
                    }
                }
            }
        }
        //二次确认是否有用户信息
        if (user == null) {
            response.sendRedirect("http://127.0.0.1:8080/login?url=http://127.0.0.1:8081/login");
        }
    }
}
