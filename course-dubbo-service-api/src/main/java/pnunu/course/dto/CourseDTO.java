package pnunu.course.dto;

import lombok.Data;
import pnunu.user.dto.TeacherDTO;

import java.io.Serializable;

/**
 * @Author: pnunu
 * @Date: 2019/3/7 19:40
 */
@Data
public class CourseDTO implements Serializable {
    private static final long serialVersionUID = 8048837803687847904L;
    private int id;
    private String title;
    private String description;
    private TeacherDTO teacherDTO;
}
