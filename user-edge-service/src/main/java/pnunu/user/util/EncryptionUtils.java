package pnunu.user.util;

import org.apache.tomcat.util.buf.HexUtils;

import java.security.MessageDigest;

/**
 * @Author: pnunu
 * @Date: Created in 16:48 2018/8/8
 * @Description: 加密工具类
 */
public class EncryptionUtils {

    private static final String MD5 = "MD5";
    private static final String UTF8 = "utf-8";

    public static String md5(String password) {
        try {
            MessageDigest md5 = MessageDigest.getInstance(MD5);
            byte[] md5Bytes = md5.digest(password.getBytes(UTF8));
            return HexUtils.toHexString(md5Bytes);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;

    }
}
