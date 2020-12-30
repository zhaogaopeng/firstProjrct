package com.zgp.demo.Service;

import com.zgp.demo.common.Constants;
import com.zgp.demo.common.RedisCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
public class loginService {

    @Autowired
    private RedisCache redisCache;

    /**
     * 登录验证
     * @param username
     * @param password
     * @param code
     * @param uuid
     * @return
     */
    public String login(String id,String name){
        redisCache.setCacheObject(id,name);
        String cacheObject = (String) redisCache.getCacheObject(id);
        return cacheObject;
    }
}
