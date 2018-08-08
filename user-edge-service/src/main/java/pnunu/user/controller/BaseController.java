package pnunu.user.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import pnunu.user.response.Message;
import pnunu.user.util.RandomUtils;

import javax.servlet.http.HttpServletRequest;

/**
 * 控制器支持类
 * @author pnunu
 * @version 2018-8-8
 */
public abstract class BaseController {

    /**
     * 日志对象
     */
    protected Logger logger = LoggerFactory.getLogger(getClass());

    @ExceptionHandler
    @ResponseBody
    public Message exp(HttpServletRequest request, Exception ex) {
        Message msg = Message.error(ex.getMessage());
        logger.error(request.getRequestURL().toString() + ", \r\n error:" + ex.getMessage());
        return msg;
    }

    protected String getToken() {
        return RandomUtils.randomCode();
    }

}
