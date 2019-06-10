package pnunu.course;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import pnunu.course.filter.CourseFilter;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author: pnunu
 * @Date: Created in 15:21 2018/8/8
 * @Description: course-edge-service
 */
@SpringBootApplication
public class CourseEdgeServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(CourseEdgeServiceApplication.class);
    }

    @Bean
    public FilterRegistrationBean filterRegistrationBean() {
        FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean();
        CourseFilter courseFilter = new CourseFilter();
        filterRegistrationBean.setFilter(courseFilter);
        List<String> urlPatterns = new ArrayList<>();
        urlPatterns.add("/*");
        filterRegistrationBean.setUrlPatterns(urlPatterns);
        return filterRegistrationBean;
    }
}
