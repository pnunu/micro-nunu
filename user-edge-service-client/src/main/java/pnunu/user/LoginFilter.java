package pnunu.user;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import pnunu.user.dto.UserDTO;

import javax.servlet.*;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.util.concurrent.TimeUnit;

/**
 * @Author: pnunu
 * @Date: 2018/10/19 21:47
 * @Description: 用户登录filter
 */
public abstract class LoginFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    private static Cache<String, UserDTO> cache = CacheBuilder.newBuilder().maximumSize(10000)
            .expireAfterAccess(3, TimeUnit.HOURS).build();

    private static String TOKEN_NAME = "token";
    private static String AUTHENTICATION_URL = "http://127.0.0.1:8082/";

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
            throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        String token = request.getParameter(TOKEN_NAME);
        if (StringUtils.isBlank(token)) {
            Cookie[] cookies = request.getCookies();
            if (cookies != null) {
                for (Cookie c : cookies) {
                    if (c.getName().equals(TOKEN_NAME)) {
                        token = c.getValue();
                    }
                }
            }
        }
        UserDTO userDTO = null;
        if (StringUtils.isNotBlank(token)) {
            userDTO = cache.getIfPresent(token);
            if (null == userDTO) {
                userDTO = requestUserInfo(token);
                if (userDTO != null) {
                    //// 存入缓存
                    cache.put(token, userDTO);
                }
            }
        }
        if (userDTO == null) {
            response.sendRedirect(AUTHENTICATION_URL + "user/login");
            return;
        }


        login(request, response, userDTO);
        filterChain.doFilter(request, response);
    }

    protected abstract void login(HttpServletRequest request, HttpServletResponse response, UserDTO userDTO);

    private UserDTO requestUserInfo(String token) {
        String url = AUTHENTICATION_URL + "user/authentication";
        HttpClient client = new DefaultHttpClient();
        HttpPost post = new HttpPost(url);
        post.addHeader(TOKEN_NAME, token);
        InputStream inputStream = null;
        try {
            HttpResponse response = client.execute(post);
            if (HttpStatus.SC_OK != response.getStatusLine().getStatusCode()) {
                throw new RuntimeException("status line: " + response.getStatusLine().getStatusCode());
            }
            inputStream = response.getEntity().getContent();
            byte[] temp = new byte[1024];
            StringBuilder sb = new StringBuilder();
            int len = 0;
            while ((len = inputStream.read(temp)) > 0) {
                sb.append(new String(temp, 0, len));
            }
            UserDTO userDTO = new ObjectMapper().readValue(sb.toString(), UserDTO.class);
            return userDTO;
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (null != inputStream) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }
        return null;
    }

    @Override
    public void destroy() {

    }
}
