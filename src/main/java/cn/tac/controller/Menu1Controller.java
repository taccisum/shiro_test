package cn.tac.controller;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author : tac
 * @date : 2017/7/28
 */

@Controller
@RequestMapping("menu1")
public class Menu1Controller {

    @RequestMapping("/page1")
    @RequiresPermissions("menu1:page1:view")
    public String page1(){
        return "/menu1/Page1.html";
    }

    @RequestMapping("/page2")
    @RequiresPermissions("menu1:page2:view")
    public String page2(){
        return "/menu1/Page2.html";
    }

}
