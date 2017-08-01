package cn.tac.test.shiro.controller;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author : tac
 * @date : 2017/7/28
 */

@RequestMapping("security")
@Controller
public class SecurityController {

    @RequestMapping("login")
    public String login(){
        return "/login.html";
    }

    @RequestMapping("doLogin")
    @ResponseBody
    public String doLogin(){
        Subject subject = SecurityUtils.getSubject();
        UsernamePasswordToken token = new UsernamePasswordToken("tac", "123456");
        subject.login(token);
        return "登陆成功";
    }

    @RequestMapping("unauthorized")
    public String unauthorized(){
        return "/unauthorized.html";
    }

}
