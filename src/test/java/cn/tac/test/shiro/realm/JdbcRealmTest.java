package cn.tac.test.shiro.realm;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import com.mysql.jdbc.Driver;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.mgt.DefaultSecurityManager;
import org.apache.shiro.realm.jdbc.JdbcRealm;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.ThreadContext;
import org.junit.Assert;
import org.junit.Test;

import java.beans.PropertyVetoException;

/**
 * @author : tac
 * @date : 26/07/2017
 */
public class JdbcRealmTest {

    @Test
    public void testSimply() throws PropertyVetoException {
        DefaultSecurityManager securityManager = new DefaultSecurityManager();
        JdbcRealm jdbcRealm = new JdbcRealm();
        ComboPooledDataSource dataSource = new ComboPooledDataSource();
        dataSource.setJdbcUrl("jdbc:mysql://localhost:3306/shiro_test?useSSL=false");
        dataSource.setUser("root");
        dataSource.setDriverClass(Driver.class.getName());
        jdbcRealm.setDataSource(dataSource);
        securityManager.setRealm(jdbcRealm);

        SecurityUtils.setSecurityManager(securityManager);
        Subject subject = SecurityUtils.getSubject();
        UsernamePasswordToken token = new UsernamePasswordToken("tac", "321");

        try {
            subject.login(token);
        } catch (AuthenticationException e) {
        }
        Assert.assertEquals(true, subject.isAuthenticated());
        subject.logout();
        ThreadContext.unbindSubject();
        ThreadContext.unbindSecurityManager();
    }
}