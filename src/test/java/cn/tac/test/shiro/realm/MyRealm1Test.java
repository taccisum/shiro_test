package cn.tac.test.shiro.realm;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.mgt.DefaultSecurityManager;
import org.apache.shiro.subject.Subject;
import org.junit.Assert;
import org.junit.Test;

/**
 * @author : tac
 * @date : 2017/7/27
 */
public class MyRealm1Test {

    @Test
    public void testSimply() {
        DefaultSecurityManager securityManager = new DefaultSecurityManager();
        securityManager.setRealm(new MyRealm1());
        SecurityUtils.setSecurityManager(securityManager);
        Subject subject = SecurityUtils.getSubject();

        UsernamePasswordToken token = new UsernamePasswordToken("myRealm1", "hhhhh");
        subject.login(token);
        subject.checkPermission("myRealm:create");
        subject.checkPermission("myRealm:delete");
        subject.checkPermission("myRealm:update");
        Assert.assertFalse(subject.isPermitted("myRealm:view"));

        subject.checkRole("role1");
        subject.checkRole("role2");
        Assert.assertFalse(subject.hasRole("role3"));
    }
}