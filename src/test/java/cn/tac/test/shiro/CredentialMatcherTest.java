package cn.tac.test.shiro;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authc.credential.DefaultPasswordService;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.authc.credential.PasswordMatcher;
import org.apache.shiro.authc.credential.PasswordService;
import org.apache.shiro.crypto.hash.DefaultHashService;
import org.apache.shiro.crypto.hash.format.HexFormat;
import org.apache.shiro.mgt.DefaultSecurityManager;
import org.apache.shiro.realm.text.IniRealm;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.ByteSource;
import org.apache.shiro.util.ThreadContext;
import org.junit.Assert;
import org.junit.Test;


/**
 * @author : tac
 * @date : 27/07/2017
 */
public class CredentialMatcherTest {
    @Test
    public void testSimply() {
        PasswordService service = new DefaultPasswordService();
        String encrypt = service.encryptPassword("123456");
        System.out.println(encrypt);
        Assert.assertTrue(service.passwordsMatch("123456", encrypt));
    }

    @Test
    public void testCredentialsMatcher() {
        DefaultSecurityManager securityManager = new DefaultSecurityManager();
        IniRealm iniRealm = new IniRealm("classpath:shiro-encrypt.ini");
        iniRealm.setCredentialsMatcher(new PasswordMatcher());
        securityManager.setRealm(iniRealm);
        SecurityUtils.setSecurityManager(securityManager);
        Subject subject = SecurityUtils.getSubject();
        UsernamePasswordToken token = new UsernamePasswordToken("liao", "123456");
        subject.login(token);
        subject.logout();
        ThreadContext.unbindSubject();
    }

    @Test
    public void testHashedCredentialsMatcher() {
        DefaultSecurityManager securityManager = new DefaultSecurityManager();
        IniRealm iniRealm = new MyTestIniRealm("classpath:shiro-encrypt.ini");
        HashedCredentialsMatcher credentialsMatcher = new HashedCredentialsMatcher();
        credentialsMatcher.setHashAlgorithmName("md5");
        credentialsMatcher.setHashIterations(2);
        iniRealm.setCredentialsMatcher(credentialsMatcher);
        securityManager.setRealm(iniRealm);
        SecurityUtils.setSecurityManager(securityManager);
        Subject subject = SecurityUtils.getSubject();
        UsernamePasswordToken token = new UsernamePasswordToken("tac", "123456");
        subject.login(token);
        subject.logout();
        ThreadContext.unbindSubject();
    }

    @Test
    public void testHashPasswordService() {
        DefaultPasswordService passwordService = new DefaultPasswordService();
        DefaultHashService hashService = new DefaultHashService();
        hashService.setHashIterations(1);
        hashService.setHashAlgorithmName("MD5");
        passwordService.setHashService(hashService);
        passwordService.setHashFormat(new HexFormat());
        passwordService.setHashFormatFactory(null);
        System.out.println(passwordService.encryptPassword("123456"));
    }

    class MyTestIniRealm extends IniRealm {
        public MyTestIniRealm(String resourcePath) {
            super(resourcePath);
        }

        @Override
        protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
            SimpleAccount account = (SimpleAccount) super.doGetAuthenticationInfo(token);
            account.setCredentialsSalt(ByteSource.Util.bytes("tac" + "506b8bb7b88e7f3d43c4a53da57aba20"));
            return account;
        }
    }
}
