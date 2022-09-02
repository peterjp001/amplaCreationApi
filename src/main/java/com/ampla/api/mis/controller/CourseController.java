package com.ampla.api.mis.controller;

import com.ampla.api.job.TestMyJob;
import com.ampla.api.mis.dto.CourseEmployeeDTO;
import com.ampla.api.mis.dto.EmployeeStatusDTO;
import com.ampla.api.mis.entities.Course;
import com.ampla.api.mis.service.CourseService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class CourseController {
    protected static final Log logger = LogFactory.getLog(TestMyJob.class);
    private final CourseService courseService;

    public CourseController(CourseService courseService) {
        this.courseService = courseService;
    }

    @PostMapping(path="/course")
    @PostAuthorize("hasAnyAuthority('USER')")
    public Course newCourse(@RequestBody Course course){
        return courseService.SaveCourse(course);
    }

    @GetMapping(path="/courses")
    @PostAuthorize("hasAnyAuthority('USER')")
    public List<Course> listCourse(){
        return courseService.listCourse();
    }

    @GetMapping(path = "/course/{id}")
    @PostAuthorize("hasAnyAuthority('USER')")
    public Optional<Course> getOneCourse(@PathVariable("id") Long id){
        return courseService.getCourseById(id);
    }

    @PostMapping(path="/linkCourseToEmployee")
    @PostAuthorize("hasAnyAuthority('ADMIN')")
    public void courseToEmployee(@RequestBody CourseEmployeeDTO courseEmployeeDTO ){
        courseService.linkEmployeeToCourse(courseEmployeeDTO.getIdEmployee(),courseEmployeeDTO.getIdCourse());
    }



}
