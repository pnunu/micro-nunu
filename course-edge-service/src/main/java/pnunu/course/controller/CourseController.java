package pnunu.course.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import pnunu.course.dto.CourseDTO;
import pnunu.course.service.ICourseService;
import pnunu.user.dto.UserDTO;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @Author: pnunu
 * @Date: 2019/6/8 19:20
 * @Description: 课程服务 controller
 */
@Controller
public class CourseController {

    @Reference
    private ICourseService iCourseService;

    @RequestMapping(value = "courseList", method = RequestMethod.GET)
    public List<CourseDTO> courseList(HttpServletRequest request) {
        UserDTO dto = (UserDTO) request.getAttribute("user");
        System.out.println(dto.toString());
        return iCourseService.courseList();
    }
}
