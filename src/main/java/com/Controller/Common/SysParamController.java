package com.Controller.Common;


import com.Dao.Entity.Common.SysParam;
import com.Service.Common.SysParamService;
import com.Service.RedisService.RedisCacheService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@Controller
@ComponentScan("com.Service.Common")
public class SysParamController {

    private Logger logger= LoggerFactory.getLogger(SysParamController.class);

    @Resource
    private SysParamService sysParamService;

    @Resource
    private RedisCacheService redisCacheService;

    @RequestMapping(value = "/sysparam/save",method= RequestMethod.POST)
    public String save(SysParam sysParam){
        logger.info("---------------111");
        logger.info("---------------"+sysParam.toString());
        sysParamService.insert(sysParam);
        return "success";
    }


    @RequestMapping(value = "/hello/1",method= RequestMethod.GET)
    public void hello(){
        logger.info("---------------hello");
    }

    @RequestMapping(value = "/redis/1",method= RequestMethod.GET)
    public String saveRedis(){
        logger.info("redis begin");
        boolean flag=redisCacheService.putSessionObject("swq","swqObject");
        logger.info("redis over---"+flag);
        return "success";
    }

    @RequestMapping(value = "/redis/get",method= RequestMethod.GET)
    public String getRedis(){
        logger.info("redis get begin");
        String reuslt= (String) redisCacheService.getsessionObject("swq");
        logger.info("redis over---"+reuslt);
        return "success";
    }

}
