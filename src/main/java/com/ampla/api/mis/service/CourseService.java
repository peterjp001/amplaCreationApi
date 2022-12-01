package com.ampla.api.mis.service;

import com.ampla.api.exception.DataNotFoundException;
import com.ampla.api.mis.entities.Course;

import java.util.List;

public interface CourseService {

    Course SaveCourse(Course course);


    void addCourseToTeacher(Long idEmployee, Long idCourse);

    List<Course> listCourse();

    Course getCourseById(Long id) throws DataNotFoundException;

    Course updateCourse(Long id, Course c) throws DataNotFoundException;


    Course getCourseByName(String courseName);
}
