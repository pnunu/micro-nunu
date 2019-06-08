package pnunu.course.service;

import pnunu.course.dto.CourseDTO;

import java.util.List;

/**
 * @Author: pnunu
 * @Date: 2019/3/7 19:39
 */
public interface ICourseService {

    /**
     * 课程列表
     * @return list
     */
    List<CourseDTO> courseList();
}
