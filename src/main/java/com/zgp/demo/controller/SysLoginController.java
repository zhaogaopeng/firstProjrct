package com.zgp.demo.controller;

import com.zgp.demo.entity.AjaxResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class SysLoginController {

    /**
     * 登录
     */
    @GetMapping("/login")
    public AjaxResult firstSatrt(){
        AjaxResult ajax = AjaxResult.success();
        ajax.put("uuid","9998444");
        return ajax;
    }


}
