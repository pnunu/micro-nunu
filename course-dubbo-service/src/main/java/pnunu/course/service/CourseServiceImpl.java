package pnunu.course.service;

import org.apache.thrift.TException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pnunu.course.dto.CourseDTO;
import pnunu.course.mapper.CourseMapper;
import pnunu.thrift.user.UserInfo;
import pnunu.user.dto.TeacherDTO;

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

    @Autowired
    private TeacherUserServiceProvider teacherUserServiceProvider;

    @Override
    public List<CourseDTO> courseList() {
        List<CourseDTO> list = courseMapper.listCourse();
        if (list != null && list.size() > 0) {
            for (CourseDTO dto : list) {
                Integer teacherId = courseMapper.getCourseTeacher(dto.getId());
                if (teacherId != null) {
                    try {
                        UserInfo userInfo = teacherUserServiceProvider.getUserService().getTeacherById(teacherId);
                        dto.setTeacherDTO(trans2Teacher(userInfo));
                    } catch (TException e) {
                        e.printStackTrace();
                        return null;
                    }
                }
            }
        }
        return list;
    }

    private TeacherDTO trans2Teacher(UserInfo userInfo) {
        TeacherDTO dto = new TeacherDTO();
        BeanUtils.copyProperties(userInfo, dto);
        return dto;
    }
}
