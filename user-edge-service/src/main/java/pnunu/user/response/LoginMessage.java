package pnunu.user.response;

/**
 * @Author: pnunu
 * @Date: Created in 16:25 2018/9/19
 * @Description: 登录返回信息
 */
public class LoginMessage extends Message {
    private String tokem;

    /**
     * 默认为成功返回信息
     */
    public LoginMessage(String tokem) {
        this.tokem = tokem;
    }

    public String getTokem() {
        return tokem;
    }

    public void setTokem(String tokem) {
        this.tokem = tokem;
    }
}
