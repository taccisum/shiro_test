package cn.tac.test.shiro;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.mgt.DefaultSecurityManager;
import org.apache.shiro.realm.text.IniRealm;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.mgt.DefaultSessionKey;
import org.apache.shiro.session.mgt.DefaultSessionManager;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.ThreadContext;
import org.junit.Assert;
import org.junit.Test;

/**
 * @author : tac
 * @date : 01/08/2017
 */
public class SessionTest {
    @Test
    public void testSimply() {
        DefaultSecurityManager securityManager = new DefaultSecurityManager();
        securityManager.setRealm(new IniRealm("classpath:shiro.ini"));

        DefaultSessionManager sessionManager = new DefaultSessionManager();
        //todo::

        securityManager.setSessionManager(sessionManager);
        SecurityUtils.setSecurityManager(securityManager);
        Subject subject = SecurityUtils.getSubject();
        Assert.assertNull(subject.getSession(false));

        UsernamePasswordToken token = new UsernamePasswordToken("zhang", "123");
        try {
            subject.login(token);
        } catch (AuthenticationException e) {
        }

        Session session = subject.getSession(false);
        Assert.assertNotNull(session);
        System.out.println(session.getId());

        sessionManager.checkValid(new DefaultSessionKey(session.getId()));

        subject.logout();
        ThreadContext.unbindSubject();
        ThreadContext.unbindSecurityManager();
    }
}
