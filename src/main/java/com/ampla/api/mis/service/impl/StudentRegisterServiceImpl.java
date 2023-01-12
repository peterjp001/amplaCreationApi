package com.ampla.api.mis.service.impl;

import com.ampla.api.exception.DataNotFoundException;
import com.ampla.api.mis.dto.RegisterDto;
import com.ampla.api.mis.dto.StudentRequestDTO;
import com.ampla.api.mis.dto.StudentResponseDTO;
import com.ampla.api.mis.entities.*;
import com.ampla.api.mis.repository.StudentRegisterRepository;
import com.ampla.api.mis.service.AcademicYearService;
import com.ampla.api.mis.service.GradeService;
import com.ampla.api.mis.service.StudentRegisterService;
import com.ampla.api.mis.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Transactional
@Service
public class StudentRegisterServiceImpl implements StudentRegisterService {

    private final StudentRegisterRepository studentRegisterRepository;
    private final GradeService gradeService;

    private final StudentService studentService;
    private final AcademicYearService academicYearService;

    @Autowired
    public StudentRegisterServiceImpl(StudentRegisterRepository studentRegisterRepository, GradeService gradeService, StudentService studentService, AcademicYearService academicYearService) {
        this.studentRegisterRepository = studentRegisterRepository;
        this.gradeService = gradeService;
        this.studentService = studentService;
        this.academicYearService = academicYearService;
    }

    @Override
    public StudentResponseDTO saveStudentRegister(StudentRequestDTO dto) throws DataNotFoundException {

        Student student = new Student();

        student.setCodeStudent(dto.getCodeStudent());
        student.setFirstName(dto.getFirstName());
        student.setLastName(dto.getLastName());
        student.setBirthDate(dto.getBirthDate());
        student.setBirthPlace(dto.getBirthPlace());
        student.setSexe(dto.getSexe());
        student.setAddress(dto.getAddress());
        student.setFatherFullName(dto.getFatherFullName());
        student.setMotherFullName(dto.getMotherFullName());
        student.setResponsiblePersonFullName(dto.getResponsiblePersonFullName());
        student.setResponsiblePersonPhone(dto.getResponsiblePersonPhone());
        student.setResponsiblePersonAddress(dto.getResponsiblePersonAddress());

        Student newStudent = studentService.saveStudent(student);
        Grade grade = gradeService.getGradeByGradeName(dto.getGrade());
        AcademicYear academicYear = academicYearService.getById(dto.getAcademicYearId());

        StudentRegister register = new StudentRegister();
        register.setStudent(newStudent);
        register.setGrade(grade);
        register.setAcademicYear(academicYear);
        register.setLastGrade(dto.getLastGrade());
        register.setLastSchool(dto.getLastSchool());

        return  StudentResponseDTO.fromDto(studentRegisterRepository.save(register));
    }

    @Override
    public StudentResponseDTO saveRegister(RegisterDto dto) throws DataNotFoundException {
        StudentRegister register = new StudentRegister();

        Student student = studentService.getOneStudent(dto.getStudentId());
        register.setStudent(student);

        Grade grade = gradeService.getGradeByGradeName(dto.getGradeName());
        register.setGrade(grade);

        AcademicYear academicYear = academicYearService.getById(dto.getAcademicYearId());
        register.setAcademicYear(academicYear);

        register.setLastGrade(dto.getLastGrade());
        register.setLastSchool(dto.getLastSchool());

        return StudentResponseDTO.fromDto(studentRegisterRepository.save(register));
    }

    @Override
    public List<StudentResponseDTO> listAllStudentRegisterByAY(Long id) {
        return studentRegisterRepository.findAllByAcademicYearId(id)
                .stream()
                .map(StudentResponseDTO::fromDto)
                .collect(Collectors.toList());
    }

    @Override
    public StudentRegister updateStudentRegister(Long registerId,StudentResponseDTO register) throws DataNotFoundException {
        StudentRegister reg = getStudentRegisterById(registerId);
        System.out.println(
                register.getGradeName()
        );

        if(register != null){
            if(register.getGradeName() != null){
                Grade g = gradeService.getGradeByGradeName(register.getGradeName());
                reg.setGrade(g);
            }
            if(register.getLastSchool() != null){
                reg.setLastSchool(register.getLastSchool());
            }
            if(register.getLastGrade() != null){
                reg.setLastGrade(register.getLastGrade());
            }
        }
        return studentRegisterRepository.save(reg);
    }

    @Override
    public StudentRegister getStudentRegisterById(Long id) throws DataNotFoundException {
        return  studentRegisterRepository.findById(id).orElseThrow(
                ()->new DataNotFoundException("Student Register with ID "+id+" not found")
        );
    }

    @Override
    public void deleteStudentRegister(Long id) {

    }

    @Override
    public StudentResponseDTO getGradeRegistryByStudentIdAndAcademicYear(Long studentId, Long academicYearId) throws DataNotFoundException {
        StudentRegister studentRegister = studentRegisterRepository.findStudentRegisterByStudentIdAndAcademicYearId(studentId,academicYearId)
                .orElseThrow(()->new DataNotFoundException("Student with Id "+studentId+" not found for academic year "+academicYearId));

        return StudentResponseDTO.fromDto(studentRegister);
    }

    @Override
    public StudentResponseDTO getStudentByIdAndAcademicYear(Long registerId, Long academicYearId) {

        return StudentResponseDTO.fromDto(studentRegisterRepository.findStudentRegisterByIdAndAcademicYearId(registerId,academicYearId));
    }
}
