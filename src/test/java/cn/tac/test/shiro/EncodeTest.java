package cn.tac.test.shiro;

import org.apache.shiro.codec.Base64;
import org.apache.shiro.codec.Hex;
import org.junit.Assert;
import org.junit.Test;

/**
 * @author : tac
 * @date : 27/07/2017
 */

public class EncodeTest {

    @Test
    public void testBase64() {
        String str = "hello";
        String base64Encoded = Base64.encodeToString(str.getBytes());
        System.out.println(base64Encoded);
        String str2 = Base64.decodeToString(base64Encoded);
        Assert.assertEquals(str, str2);
    }

    @Test
    public void testHex() {
        String str = "hello";
        String hexEncoded = Hex.encodeToString(str.getBytes());
        System.out.println(hexEncoded);
        String str2 = new String(Hex.decode(hexEncoded.getBytes()));
        Assert.assertEquals(str, str2);
    }

}
