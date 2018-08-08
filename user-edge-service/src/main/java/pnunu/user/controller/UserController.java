package pnunu.user.controller;

import org.apache.thrift.TException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import pnunu.thrift.user.UserInfo;
import pnunu.user.response.Message;
import pnunu.user.thrift.ServiceProvider;
import pnunu.user.util.EncryptionUtils;

/**
 * @Author: pnunu
 * @Date: Created in 16:02 2018/8/8
 * @Description:
 */
@Controller
public class UserController extends BaseController {

    @Autowired
    private ServiceProvider serviceProvider;

    @RequestMapping(value = "login", method = RequestMethod.POST)
    public Message login(UserInfo user) {
        //1.验证用户名密码
        UserInfo userInfo = null;
        try {
            serviceProvider.getUserService().getUserByName(user.getUsername());
        } catch (TException e) {
            e.printStackTrace();
            return Message.USERNAME_PASSWORD_INVALID;
        }
        if (userInfo == null) {
            return Message.USERNAME_PASSWORD_INVALID;
        }
        if (!userInfo.getPassword().equalsIgnoreCase(EncryptionUtils.md5(user.getPassword()))) {
            return Message.USERNAME_PASSWORD_INVALID;
        }
        //2.生成token
        String token = getToken();
        //3.缓存用户
        //4.
        //5.
        return null;
    }
}
