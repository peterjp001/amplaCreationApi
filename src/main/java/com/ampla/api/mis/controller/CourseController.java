package com.ampla.api.mis.controller;

import com.ampla.api.exception.DataNotFoundException;
import com.ampla.api.job.TestMyJob;
import com.ampla.api.mis.dto.CourseEmployeeDTO;
import com.ampla.api.mis.entities.Course;
import com.ampla.api.mis.service.CourseService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;


@CrossOrigin(origins = "*")
@RestController
public class CourseController {
    protected static final Log logger = LogFactory.getLog(CourseController.class);
    private final CourseService courseService;

    public CourseController(CourseService courseService) {
        this.courseService = courseService;
    }

    @PostMapping(path="/course")
    @PostAuthorize("hasAnyAuthority('ADMIN')")
    public Course newCourse(@RequestBody @Valid Course course){
        return courseService.SaveCourse(course);
    }

    @GetMapping(path="/courses")
    @PostAuthorize("hasAnyAuthority('USER')")
    public List<Course> listCourse(){
        return courseService.listCourse();
    }

    @GetMapping(path = "/course/{id}")
    @PostAuthorize("hasAnyAuthority('USER')")
    public Course getOneCourse(@PathVariable("id") Long id) throws DataNotFoundException {
        return courseService.getCourseById(id);
    }


    @PutMapping(path="/course/{id}")
    @PostAuthorize("hasAnyAuthority('ADMIN')")
    public Course updateCource(@PathVariable("id") Long id, @RequestBody Course c) throws DataNotFoundException {
        return courseService.updateCourse(id,c);
    }

    @PostMapping(path="/add/course/{courseName}/teacher/{teacherName}")
    @PostAuthorize("hasAnyAuthority('ADMIN')")
    public void courseToEmployee(@PathVariable("courseName") String courseName, @PathVariable("teacherName") String teacherName ){
//        courseService.linkEmployeeToCourse(courseEmployeeDTO.getIdEmployee(),courseEmployeeDTO.getIdCourse());


    }




}
