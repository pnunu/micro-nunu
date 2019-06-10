package pnunu.course.filter;

import pnunu.user.LoginFilter;
import pnunu.user.dto.UserDTO;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @Author: pnunu
 * @Date: 2019/6/8 19:29
 * @Description: 课程服务
 */
public class CourseFilter extends LoginFilter {
    @Override
    protected void login(HttpServletRequest request, HttpServletResponse response, UserDTO userDTO) {
        request.setAttribute("user", userDTO);
    }
}
