package pnunu.user.dto;

import lombok.Data;

/**
 * @Author: pnunu
 * @Date: 2019/3/7 19:47
 * @Description: teacher
 */
@Data
public class TeacherDTO extends UserDTO {
    private String intro;
    private int stars;
}
