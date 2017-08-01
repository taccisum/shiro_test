package cn.tac.test.shiro.realm;

import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;

import java.util.HashSet;
import java.util.Set;

/**
 * @author : tac
 * @date : 26/07/2017
 */
public class MyRealm1 extends AuthorizingRealm {

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        String username = (String) getAvailablePrincipal(principals);
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
//        info.setRoles();
        Set<String> permissions = new HashSet<>();
        permissions.add("myRealm:create");
        permissions.add("myRealm:delete");
        permissions.add("myRealm:update");
        info.setStringPermissions(permissions);

        Set<String> roles = new HashSet<>();
        roles.add("role1");
        roles.add("role2");
        info.setRoles(roles);
        return info;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        String username = (String)token.getPrincipal();
        String password = new String((char[])token.getCredentials());
        if(!"myRealm1".equals(username)) {
            throw new UnknownAccountException("No account found for user [" + username + "]");
        }
        if(!"hhhhh".equals(password)) {
            throw new IncorrectCredentialsException();
        }
        return new SimpleAuthenticationInfo(username, password, getName());
    }
}
