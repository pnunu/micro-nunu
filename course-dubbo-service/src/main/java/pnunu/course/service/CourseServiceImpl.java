package pnunu.course.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pnunu.course.dto.CourseDTO;
import pnunu.course.mapper.CourseMapper;

import java.util.List;

/**
 * @Author: pnunu
 * @Date: 2019/3/27 19:17
 * @Description: 课程服务
 */
@Service
public class CourseServiceImpl implements ICourseService {

    @Autowired
    private CourseMapper courseMapper;

    @Override
    public List<CourseDTO> courseList() {
        List<CourseDTO> list = courseMapper.listCourse();
        for (CourseDTO dto : list) {
            Integer teacherId = courseMapper.getCourseTeacher(dto.getId());
            if (teacherId != null) {
            }
        }
        return null;
    }
}
