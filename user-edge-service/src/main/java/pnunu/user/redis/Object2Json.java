package pnunu.user.redis;

import com.google.gson.Gson;

/**
 * @Author: pnunu
 * @Date: Created in 11:38 2018/8/9
 * @Description:
 */
public class Object2Json {
    private final static Gson gson = new Gson();
    /**
     * Object转成JSON数据
     */
    public static String toJson(Object object) {
        if (object instanceof Integer || object instanceof Long || object instanceof Float ||
                object instanceof Double || object instanceof Boolean || object instanceof String) {
            return String.valueOf(object);
        }
        return gson.toJson(object);
    }

    /**
     * JSON数据，转成Object
     */
    public static <T> T fromJson(String json, Class<T> clazz) {
        return gson.fromJson(json, clazz);
    }

}
