package cn.tac.test.shiro.realm;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.config.IniSecurityManagerFactory;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.Factory;
import org.apache.shiro.util.ThreadContext;
import org.junit.Assert;
import org.junit.Test;

/**
 * @author : tac
 * @date : 26/07/2017
 */

public class IniTest {
    @Test
    public void testSimply() {
        //1、获取SecurityManager工厂，此处使用Ini配置文件初始化SecurityManager
        Factory<SecurityManager> factory =
                new IniSecurityManagerFactory("classpath:shiro.ini");
        //2、得到SecurityManager实例 并绑定给SecurityUtils
        SecurityManager securityManager = factory.getInstance();
        SecurityUtils.setSecurityManager(securityManager);
        //3、得到Subject及创建用户名/密码身份验证Token（即用户身份/凭证）
        Subject subject = SecurityUtils.getSubject();
        Assert.assertNull(subject.getSession(false));

        UsernamePasswordToken token = new UsernamePasswordToken("zhang", "123");

        Assert.assertTrue(subject.getPrincipals() == null || subject.getPrincipals().isEmpty());
        try {
            //4、登录，即身份验证
            subject.login(token);
        } catch (AuthenticationException e) {
            //5、身份验证失败
        }

        Assert.assertNotNull(subject.getSession(false));
        Assert.assertEquals(true, subject.isAuthenticated()); //断言用户已经登录
        Assert.assertFalse(subject.getPrincipals().isEmpty());
        System.out.println(subject.getPrincipal());


        //6、退出
        subject.logout();
        ThreadContext.unbindSubject();
        ThreadContext.unbindSecurityManager();
    }
}
