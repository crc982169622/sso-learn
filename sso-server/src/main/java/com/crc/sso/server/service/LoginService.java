package com.crc.sso.server.service;

import com.crc.sso.server.domain.User;

/**
 * @Author: chenrencun
 * @Date: 2019/11/25 17:52
 * @Description: 描述
 */
public interface LoginService {

    User login(String userName, String password);
}
