package com.zgp.demo.controller;

import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.RandomUtil;
import com.zgp.demo.Service.loginService;
import com.zgp.demo.entity.AjaxResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class SysLoginController {


    @Autowired
    private loginService loginService;

    /***
     * 登录
     * @return
     */
    @GetMapping("/login")
    public AjaxResult Syslogin(){
        AjaxResult ajax = AjaxResult.success();
        int i = RandomUtil.randomInt(100);
        String loginCode = loginService.login(String.valueOf(i),"ABC");
        ajax.put("uuid",loginCode);
        return ajax;
    }


}
