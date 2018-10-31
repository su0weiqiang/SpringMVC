package com.swq.Controller.User;

import com.swq.Dao.Entity.User.User;
import com.swq.Dao.Entity.User.User;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@ComponentScan("com.Service")
public class UserController {
    @RequestMapping(value = "/login")
    public String login(){
        return "login";
    }


    @RequestMapping(value = "/login",method= RequestMethod.POST)
    public String login(User user){
        Subject subject = SecurityUtils.getSubject();//获取当前用户对象
        //生成令牌(传入用户输入的账号和密码)
        UsernamePasswordToken token=new UsernamePasswordToken(user.getUsername(),user.getPassword());

        //认证登录
        try {
            //这里会加载自定义的realm
            subject.login(token);//把令牌放到login里面进行查询,如果查询账号和密码时候匹配,如果匹配就把user对象获取出来,失败就抛异常
            User userEntity= (User) subject.getPrincipal();//获取登录成功的用户对象(以前是直接去service里面查)
            //ServletActionContext.getRequest().getSession().setAttribute("user", user);
            return "success";
        } catch (Exception e) {
            //认证登录失败抛出异常
            //addActionError("用户名和密码不匹配...");
            return "login";
        }

    }
}
