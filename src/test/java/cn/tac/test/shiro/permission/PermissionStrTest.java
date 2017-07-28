package cn.tac.test.shiro.permission;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.mgt.DefaultSecurityManager;
import org.apache.shiro.realm.text.IniRealm;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.ThreadContext;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * @author : tac
 * @date : 2017/7/27
 */

public class PermissionStrTest {

    private Subject subject;

    @Before
    public void setUp(){
        DefaultSecurityManager securityManager = new DefaultSecurityManager(new IniRealm("classpath:shiro-permission.ini"));
        SecurityUtils.setSecurityManager(securityManager);
        subject = SecurityUtils.getSubject();
    }

    @After
    public void tearDown(){
        ThreadContext.unbindSubject();
        ThreadContext.unbindSecurityManager();
    }


    @Test
    public void testSimply() {
        UsernamePasswordToken token = new UsernamePasswordToken("liao", "123");
        subject.login(token);
        Assert.assertTrue(subject.isPermitted("order:view"));
        Assert.assertTrue(subject.isPermitted("order:create"));
        Assert.assertTrue(subject.isPermitted("order:update"));
        Assert.assertFalse(subject.isPermitted("order:delete"));
        subject.checkRole("role1");
        subject.checkPermissions("order:view,create,update");
    }

    @Test
    public void testAbbr() {
        UsernamePasswordToken token1 = new UsernamePasswordToken("liao", "123");
        subject.login(token1);
        subject.checkRole("role1");
        Assert.assertTrue(subject.isPermittedAll("order:view,create,update"));

        UsernamePasswordToken token2 = new UsernamePasswordToken("wang", "123");
        subject.login(token2);
        subject.checkRole("role2");
        Assert.assertFalse(subject.isPermittedAll("order:view,create,update"));
    }

    @Test
    public void testWildcard() {
        UsernamePasswordToken token = new UsernamePasswordToken("user3", "123");
        subject.login(token);
        subject.checkRole("role3");
        subject.checkPermission("order:view");
        subject.checkPermission("order:create");
        subject.checkPermission("order:delete");
        subject.checkPermission("order:update");
        subject.checkPermission("order:*");
        subject.checkPermission("order");
        subject.checkPermissions("order:view,delete,update");
    }

    @Test
    public void testLongPermissionStr() {
        UsernamePasswordToken token = new UsernamePasswordToken("user4", "123");
        subject.login(token);
        subject.checkPermissions("root:system:biz1:order:view,create");
        subject.checkPermissions("root:system:biz2:order:view,download");
    }
}
