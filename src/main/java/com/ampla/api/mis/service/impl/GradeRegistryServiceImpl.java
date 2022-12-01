package com.ampla.api.mis.service.impl;

import com.ampla.api.exception.DataNotFoundException;
import com.ampla.api.mis.dto.CourseRegistryRequestDTO;
import com.ampla.api.mis.dto.GradeRegistryRequestDTO;
import com.ampla.api.mis.dto.GradeRegistryResponseDTO;
import com.ampla.api.mis.entities.*;
import com.ampla.api.mis.repository.GradeRegistryRepository;
import com.ampla.api.mis.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class GradeRegistryServiceImpl implements GradeRegistryService {

    private final GradeRegistryRepository registryRepository;

    private final GradeService gradeService;

    private final CourseService courseService;

    private final AcademicYearService academicYearService;

    private final EmployeeService employeeService;

    @Autowired
    public GradeRegistryServiceImpl(GradeRegistryRepository registryRepository, GradeService gradeService, CourseService courseService, AcademicYearService academicYearService, EmployeeService employeeService) {
        this.registryRepository = registryRepository;
        this.gradeService = gradeService;
        this.courseService = courseService;
        this.academicYearService = academicYearService;
        this.employeeService = employeeService;
    }

    @Override
    public void saveGradeRegistry(GradeRegistryRequestDTO gradeRegistry) throws DataNotFoundException {

        List<GradeRegistry> grList = new ArrayList<>();
        Grade grade = gradeService.getGradeByGradeName(gradeRegistry.getGradeName());
        AcademicYear academicYear = academicYearService.getById(gradeRegistry.getAcademicYearId());

        gradeRegistry.getListCourses().forEach(courseRegistry->{

            GradeRegistry gr = new GradeRegistry();
            gr.setGradeId(grade.getId());

            Course course = courseService.getCourseByName(courseRegistry.getCourseName());
            gr.setCourse(course);

            gr.setAcademicYear(academicYear);

            try {
                Employee employee = employeeService.getEmployeeByCode(courseRegistry.getCodeEmployee());
                gr.setEmployee(employee);
            } catch (DataNotFoundException e) {
                throw new RuntimeException(e);
            }

            gr.setTimeStart(courseRegistry.getTimeStart());
            gr.setTimeEnd(courseRegistry.getTimeEnd());


            grList.add(gr);
        });
        registryRepository.saveAll(grList);

    }

    @Override
    public List<GradeRegistryResponseDTO> listGradeRegistry() {
        return null;
    }

    @Override
    public GradeRegistry getGradeRegistryById(Long id) throws DataNotFoundException {
        return registryRepository.findById(id).orElseThrow(
                ()-> new DataNotFoundException("Grade Registry with Id "+id+" not exist!")
        );
    }

    @Override
    public List<GradeRegistry> listGradeRegistryByGradeId(Long id) {
        return registryRepository.findGradeRegistriesByGradeId(id);
    }

    @Override
    public GradeRegistry updateGradeRegistry(Long id, CourseRegistryRequestDTO gradeRegistry) throws DataNotFoundException {
        GradeRegistry gr = registryRepository.findById(id).orElseThrow(
                ()-> new DataNotFoundException("Grade Registry with Id "+id+" not exist!"));

        if(gradeRegistry != null){
            if(gradeRegistry.getCodeEmployee() != null){
                Employee employee= employeeService.getEmployeeByCode(gradeRegistry.getCodeEmployee());
                gr.setEmployee(employee);
            }
            if(gradeRegistry.getCourseName() != null){
                Course course = courseService.getCourseByName(gradeRegistry.getCourseName());
                gr.setCourse(course);
            }
            if(gradeRegistry.getTimeStart() != null){
                gr.setTimeStart(gradeRegistry.getTimeStart());
            }
            if(gradeRegistry.getTimeEnd() != null){
                gr.setTimeEnd(gradeRegistry.getTimeEnd());
            }
        }
        return registryRepository.save(gr);
    }
}
