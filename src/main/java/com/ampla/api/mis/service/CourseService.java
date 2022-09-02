package com.ampla.api.mis.service;

import com.ampla.api.mis.entities.Course;

import java.util.List;
import java.util.Optional;

public interface CourseService {

    Course SaveCourse(Course course);


    void linkEmployeeToCourse(Long idEmployee, Long idCourse);

    List<Course> listCourse();

    Optional<Course> getCourseById(Long id);
}
