package cn.tac.test.shiro.realm;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.config.IniSecurityManagerFactory;
import org.apache.shiro.mgt.DefaultSecurityManager;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.realm.text.IniRealm;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.Factory;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Collection;

/**
 * @author : tac
 * @date : 26/07/2017
 */
public class MyFirstRealmTest {

    @Test
    public void testSimply() {
        //1、获取SecurityManager工厂，此处使用Ini配置文件初始化SecurityManager
        Factory<SecurityManager> factory =
                new IniSecurityManagerFactory("classpath:shiro-realm.ini");
        //2、得到SecurityManager实例 并绑定给SecurityUtils
        SecurityManager securityManager = factory.getInstance();
        SecurityUtils.setSecurityManager(securityManager);
        //3、得到Subject及创建用户名/密码身份验证Token（即用户身份/凭证）
        Subject subject = SecurityUtils.getSubject();
        UsernamePasswordToken token = new UsernamePasswordToken("liao", "321");

        try {
            //4、登录，即身份验证
            subject.login(token);
        } catch (AuthenticationException e) {
            //5、身份验证失败
        }

        Assert.assertEquals(true, subject.isAuthenticated()); //断言用户已经登录

        //6、退出
        subject.logout();

    }

    @Test
    public void testWithoutFactory() {
        SecurityManager securityManager = new DefaultSecurityManager(new MyFirstRealm());
        SecurityUtils.setSecurityManager(securityManager);
        Subject subject = SecurityUtils.getSubject();
        UsernamePasswordToken token = new UsernamePasswordToken("zhang", "123");

        try {
            subject.login(token);
        } catch (AuthenticationException e) {
        }

        Assert.assertEquals(true, subject.isAuthenticated()); //断言用户已经登录
        subject.logout();
    }

    @Test
    public void testMultipleRealm() {
        DefaultSecurityManager securityManager = new DefaultSecurityManager();
        Collection<Realm> realms = new ArrayList<>();
        realms.add(new MyFirstRealm());
        realms.add(new IniRealm("classpath:shiro.ini"));
        securityManager.setRealms(realms);
        SecurityUtils.setSecurityManager(securityManager);
        Subject subject = SecurityUtils.getSubject();

        Collection<UsernamePasswordToken> tokens = new ArrayList<>();
        tokens.add(new UsernamePasswordToken("zhang", "123"));
        tokens.add(new UsernamePasswordToken("liao", "321"));
        tokens.add(new UsernamePasswordToken("wang", "123"));

        for (UsernamePasswordToken token : tokens){
            try {
                subject.login(token);
            } catch (AuthenticationException e) {
            }

            Assert.assertEquals(true, subject.isAuthenticated()); //断言用户已经登录
            subject.logout();
        }
    }

}