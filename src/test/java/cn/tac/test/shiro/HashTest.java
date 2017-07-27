package cn.tac.test.shiro;

import org.apache.shiro.crypto.hash.Md5Hash;
import org.apache.shiro.crypto.hash.Sha256Hash;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.junit.Test;

/**
 * @author : tac
 * @date : 27/07/2017
 */

public class HashTest {

    @Test
    public void testMD5() {
        String str = "hello";
        String salt = "123";
        String md5 = new Md5Hash(str, salt).toString();//还可以转换为 toBase64()/toHex()
        System.out.println(md5);
    }

    @Test
    public void testSHA256() {
        String str = "hello";
        String salt = "123";
        String sha = new Sha256Hash(str, salt).toString();
        System.out.println(sha);
    }

    @Test
    public void testSimpleHash() {
        String str = "hello";
        String salt = "123";
        //内部使用MessageDigest
        SimpleHash simpleHash = new SimpleHash("MD5", str, salt);
        System.out.println(simpleHash.toString());
        System.out.println(simpleHash.toBase64());
        System.out.println(simpleHash.toHex());
    }

}
