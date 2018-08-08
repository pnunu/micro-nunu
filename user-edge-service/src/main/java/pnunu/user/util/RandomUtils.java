package pnunu.user.util;

import java.util.Random;

/**
 * @Author: pnunu
 * @Date: Created in 16:56 2018/8/8
 * @Description: 生成随机数
 */
public class RandomUtils {
    private final static String DEFAULT_CHARS = "0123456789abcdefghijklmnopqrstuvwxyz";
    private final static int DEFAULT_LENGTH = 32;

    /** 默认是32为的0-9，a-z */
    public static String randomCode() {
        return randomCode(DEFAULT_CHARS, DEFAULT_LENGTH);
    }
    public static String randomCode(String chars, int length) {
        StringBuilder result = new StringBuilder(length);
        Random random = new Random();
        for (int i = 0; i < length; i++) {
            int loc = random.nextInt(chars.length());
            result.append(chars.charAt(loc));
        }
        return result.toString();
    }

}
