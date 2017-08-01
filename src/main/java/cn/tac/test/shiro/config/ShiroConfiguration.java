package cn.tac.test.shiro.config;

import org.apache.shiro.authc.credential.PasswordMatcher;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.realm.text.IniRealm;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author : tac
 * @date : 2017/7/28
 */

@Configuration
public class ShiroConfiguration {

    @Bean
    public ShiroFilterFactoryBean shiroFilterFactoryBean(SecurityManager securityManager){
        ShiroFilterFactoryBean bean = new ShiroFilterFactoryBean();
        bean.setSecurityManager(securityManager);
        bean.setLoginUrl("/security/login");
        bean.setSuccessUrl("/");
        bean.setUnauthorizedUrl("/security/unauthorized");
        bean.setFilterChainDefinitions("/security/**=anon\r\n/**=authc");
        return bean;
    }

    @Bean
    public SecurityManager securityManager(){
        DefaultWebSecurityManager bean = new DefaultWebSecurityManager();
        IniRealm iniRealm = new IniRealm("classpath:shiro-web.ini");
        iniRealm.setCredentialsMatcher(new PasswordMatcher());
        bean.setRealm(iniRealm);
        return bean;
    }

    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(SecurityManager securityManager){
        AuthorizationAttributeSourceAdvisor advisor = new AuthorizationAttributeSourceAdvisor();
        advisor.setSecurityManager(securityManager);
        return advisor;
    }
}
