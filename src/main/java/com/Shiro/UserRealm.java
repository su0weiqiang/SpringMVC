package com.Shiro;

import com.Dao.Entity.User.User;
import com.Service.Common.UserSystemService;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;

public class UserRealm extends AuthorizingRealm {
    //用户自己定义登陆验证器。

    @Autowired
    UserSystemService userSystemService;


    /**
     * 用户授权认证
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        return null;
    }

    /**
     * 用户登陆认证
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        UsernamePasswordToken usertoken=(UsernamePasswordToken) authenticationToken;//获取令牌(里面存放new UsernamePasswordToken放入的账号和密码)

        //得到账号和密码
        String username = usertoken.getUsername();

        User findusername = userSystemService.getUserByUserName(username);//去sql查询用户名是否存在,如果存在返回对象(账号和密码都有的对象)

        if(findusername!=null)//如果用户名存在
        {
            //参数1.用户认证的对象(subject.getPrincipal();返回的对象),
            //参数2.从数据库根据用户名查询到的用户的密码
            //参数3.把当前自定义的realm对象传给SimpleAuthenticationInfo,在配置文件需要注入
            AuthenticationInfo Info = new SimpleAuthenticationInfo(findusername, findusername.getPassword(),this.getName());
            return Info;

        }else
        {
            return null;
        }
    }

}
