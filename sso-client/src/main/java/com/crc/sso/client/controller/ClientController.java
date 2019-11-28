package com.crc.sso.client.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: chenrencun
 * @Date: 2019/11/26 9:46
 * @Description: 描述
 */
@RestController
public class ClientController {

    @RequestMapping("/login")
    public String index() {
        return "heihei";
    }
}
