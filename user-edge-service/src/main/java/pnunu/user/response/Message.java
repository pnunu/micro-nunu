package pnunu.user.response;

import org.apache.http.HttpStatus;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class Message implements Serializable {

    private static final long serialVersionUID = 1182261373964419951L;
    /** 用户名或者密码错误 */
    public static final Message USERNAME_PASSWORD_INVALID = Message.error("用户名或者密码错误");
    private int code;
    private String msg;
    private Map<String, Object> data = new HashMap<String, Object>();

    /** 默认为成功返回信息 */
    public Message() {
        this.code = HttpStatus.SC_OK;
    }

    public Message(int code) {
        this.code = code;
    }

    /** 默认失败返回信息 */
    public static Message error() {
        return error(HttpStatus.SC_INTERNAL_SERVER_ERROR, "未知异常，请联系管理员");
    }
    /** 失败返回返回信息 */
    public static Message error(String msg) {
        return error(HttpStatus.SC_INTERNAL_SERVER_ERROR, msg);
    }

    /** 失败返回信息 */
    public static Message error(int code, String msg) {
        Message message = new Message(code, msg);
        return message;
    }

    /** 成功返回信息 */
    public static Message ok(String msg) {
        Message message = new Message(HttpStatus.SC_OK, msg);
        return message;
    }

    /** 成功返回信息 */
    public static Message ok() {
        return new Message();
    }

    /** 自定义返回信息 */
    public Message(int code, String msg, Map<String, Object> data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public Message(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Map<String, Object> getData() {
        return data;
    }

    public void setData(Map<String, Object> data) {
        this.data = data;
    }

    public void addData(String key, Object value) {
        data.put(key, value);
    }
}
