package com.ampla.api.mis.service.impl;

import com.ampla.api.job.TestMyJob;
import com.ampla.api.mis.entities.Course;
import com.ampla.api.mis.entities.Employee;
import com.ampla.api.mis.repository.CourseRepository;
import com.ampla.api.mis.repository.EmployeeRepository;
import com.ampla.api.mis.service.CourseService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CourseServiceImpl implements CourseService {

    protected static final Log logger = LogFactory.getLog(TestMyJob.class);
    private final CourseRepository courseRepository;

    private final EmployeeRepository emplRepo;

    public CourseServiceImpl(CourseRepository courseRepository, EmployeeRepository emplRepo) {
        this.courseRepository = courseRepository;
        this.emplRepo = emplRepo;
    }

    @Override
    public Course SaveCourse(Course course) {
        return courseRepository.save(course);
    }

    @Override
    public void linkEmployeeToCourse(Long idEmployee, Long idCourse) {
        Optional<Employee> emp = emplRepo.findById(idEmployee);
        Optional<Course> course = courseRepository.findById(idCourse);
        if (emp.isPresent() && course.isPresent()) {
            Employee employee = emp.get();
            Course c = course.get();
            employee.getCourse().add(c);
            emplRepo.save(employee);
        }

    }

    @Override
    public List<Course> listCourse() {
        return courseRepository.findAll();
    }

    @Override
    public Optional<Course> getCourseById(Long id) {
        return courseRepository.findById(id);
    }
}