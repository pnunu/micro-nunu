package pnunu.user.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;

/**
 * @Author: pnunu
 * @Date: Created in 17:24 2018/8/9
 * @Description: 测试流的读取
 */
@Controller
public class IndexController extends BaseController {

//    @ResponseBody
    @RequestMapping(value = "/a", method = RequestMethod.GET)
    public void index(HttpServletRequest request, HttpServletResponse response) throws Exception {
        doGet(request, response);
        return;
    }

    private void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        String getContextPath = request.getContextPath();
        out.println("getContextPath:" + getContextPath + "<br>");
        String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + getContextPath + "/";
        out.println("basePath:" + basePath + "<br>");
        String getRemoteAddress = request.getRemoteAddr();
        out.println("getRemoteAddress:" + getRemoteAddress + "<br>");
        String getServletPath = request.getServletPath();
        out.println("getServletPath:" + getServletPath + "<br>");
        String getServletContextGetRealPath = request.getServletContext().getRealPath("/");
        out.println("getServletContextGetRealPath:" + getServletContextGetRealPath + "<br>");
        String getRequestURL = request.getRequestURL().toString();
        out.println("getRequestURL:" + getRequestURL + "<br>");
        String getRequestURI = request.getRequestURI();
        out.println("getRequestURI:" + getRequestURI + "<br>");
        String getQueryString = request.getQueryString();
        out.println("getQueryString:" + getQueryString + "<br>");
        String getRemoteUser = request.getRemoteUser();
        out.println("getRemoteUser:" + getRemoteUser + "<br>");

//        ServletContext().getResourceAsStream("");//改方法读不到配置文件
//        InputStream inputStream = request.getServletContext().getResourceAsStream("/application.properties");
        InputStream inputStream = this.getClass().getResourceAsStream("/application.properties");
        byte[] bytes = new byte[inputStream.available()];
        inputStream.read(bytes);
        String result  = new String(bytes);
        out.println("result:" + result + "<br>");

        out.flush();
        out.close();
    }
}
