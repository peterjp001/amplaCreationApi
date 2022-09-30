package com.ampla.api.mis.service.impl;

import com.ampla.api.exception.DataAlreadyExistException;
import com.ampla.api.exception.DataNotFoundException;
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
        Optional<Course> c = Optional.ofNullable(courseRepository.findByCourseName(course.getCourseName()));
        if ( c.isPresent())  throw new DataAlreadyExistException("Cours "+course.getCourseName()+" existe déjà");
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
    public Course getCourseById(Long id) throws DataNotFoundException {
        Optional<Course> course =  courseRepository.findById(id);
        if (course.isEmpty())
            throw new DataNotFoundException("Course with id "+id+" not found");

        return course.get();
    }



    @Override
    public Course updateCourse(Long id, Course c) throws DataNotFoundException {
        Optional<Course> course =  courseRepository.findById(id);
        if (course.isEmpty())
            throw new DataNotFoundException("Course with id "+id+" not found");
        Course courseUpdated = course.get();
        if (c.getCourseName() != null)
            courseUpdated.setCourseName(c.getCourseName());

        return courseRepository.save(courseUpdated);
    }
}
