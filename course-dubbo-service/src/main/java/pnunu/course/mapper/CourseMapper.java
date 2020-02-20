package pnunu.course.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import pnunu.course.dto.CourseDTO;

import java.util.List;

/**
 * @Author: pnunu
 * @Date: 2019/3/8 10:01
 * @Description: 课程
 */
@Mapper
public interface CourseMapper {

    @Select("select * from pe_course")
    List<CourseDTO> listCourse();

    @Select("select user_id from pr_user_course where course_id = #{courseId}")
    Integer getCourseTeacher(@Param("courseId") int courseId);
}
