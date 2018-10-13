package pnunu.user.controller;

import org.apache.commons.lang.StringUtils;
import org.apache.thrift.TException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import pnunu.thrift.user.UserInfo;
import pnunu.user.dto.UserDTO;
import pnunu.user.redis.RedisClient;
import pnunu.user.response.LoginMessage;
import pnunu.user.response.Message;
import pnunu.user.thrift.ServiceProvider;
import pnunu.user.util.EncryptionUtils;
import pnunu.user.util.RandomUtils;

/**
 * @Author: pnunu
 * @Date: Created in 16:02 2018/8/8
 * @Description: 用户
 */
@Controller
public class UserController extends BaseController {

    @Autowired
    private ServiceProvider serviceProvider;

    @Autowired
    private RedisClient redisClient;

    @ResponseBody
    @RequestMapping(value = "login", method = RequestMethod.POST)
    public Message login(UserInfo user) {
        //1.验证用户名密码
        UserInfo userInfo = null;
        try {
            userInfo = serviceProvider.getUserService().getUserByName(user.getUsername());
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
        redisClient.set(token, toDTO(userInfo), 3600);
        return new LoginMessage(token);
    }

    @ResponseBody
    @RequestMapping(value = "register", method = RequestMethod.POST)
    public Message register(UserInfo user, @RequestParam("verifyCode") String verifyCode) throws Exception {
        String code = null;
        if (user != null) {
            if (StringUtils.isBlank(user.getEmail()) && StringUtils.isBlank(user.getMobile())) {
                return Message.MOBILE_OR_EMAIL_REQUIRED;
            } else if (StringUtils.isNotEmpty(user.getMobile())) { // 电话
                code = redisClient.get(user.getMobile());
            } else if (StringUtils.isNotEmpty(user.getEmail())) {  // 邮件
                code = redisClient.get(user.getEmail());
            }
            if (StringUtils.isBlank(verifyCode)) {
                return Message.VERIFY_CODE_BLANK;
            } else {
                if (verifyCode.equals(code)) {
                    // 注册用户
                    serviceProvider.getUserService().regiserUser(user);
                    return Message.ok("注册成功");
                } else {
                    return Message.VERIFY_CODE_ERROR;
                }
            }
        }
        return Message.error();
    }

    /** 验证码 */
    @ResponseBody
    @RequestMapping(value = "/verifyCode", method = RequestMethod.POST)
    public Message sendVerifyCode(UserInfo user) throws Exception {
        String message = "Verify code is: ";
        String code = RandomUtils.randomCode("0123456789", 4);
        boolean result = false;
        if (user != null) {
            if (StringUtils.isBlank(user.getEmail()) && StringUtils.isBlank(user.getMobile())) {
                return Message.MOBILE_OR_EMAIL_REQUIRED;
            } else if (StringUtils.isNotEmpty(user.getMobile())) { // 电话
                result = serviceProvider.getMessageService().sendMobileMessage(user.getMobile(), message + code);
                redisClient.set(user.getMobile(), code);
            } else if (StringUtils.isNotEmpty(user.getEmail())) {  // 邮件
                result = serviceProvider.getMessageService().sendEmailMessage(user.getEmail(), message + code);
                redisClient.set(user.getEmail(), code);
            }
        } else {
            return Message.MOBILE_OR_EMAIL_REQUIRED;
        }
        if (result) {
            return Message.SEND_VRRIFY_FAILED;
        } else {
            return Message.ok();
        }
    }


    private UserDTO toDTO(UserInfo userInfo) {
        UserDTO userDTO = new UserDTO();
        BeanUtils.copyProperties(userInfo, userDTO);
        return userDTO;
    }
}
