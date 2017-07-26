package cn.tac.test.shiro.realm;

import org.apache.shiro.realm.jdbc.JdbcRealm;

/**
 * @author : tac
 * @date : 26/07/2017
 */
public class MyJdbcRealm extends JdbcRealm {
    @Override
    public String getName() {
        return "tac's jdbc realm";
    }
}
