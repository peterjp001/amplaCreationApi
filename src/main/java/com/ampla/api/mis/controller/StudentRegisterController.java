package com.ampla.api.mis.controller;

import com.ampla.api.exception.DataNotFoundException;
import com.ampla.api.mis.dto.StudentRequestDTO;
import com.ampla.api.mis.dto.StudentResponseDTO;
import com.ampla.api.mis.entities.StudentRegister;
import com.ampla.api.mis.service.StudentRegisterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*")
@RestController
public class StudentRegisterController {

    private final StudentRegisterService studentRegisterService;

    @Autowired
    public StudentRegisterController(StudentRegisterService studentRegisterService) {
        this.studentRegisterService = studentRegisterService;
    }

    @PostMapping(path = "/students/register")
    @PostAuthorize("hasAnyAuthority('USER')")
    public StudentResponseDTO newStudent(@RequestBody StudentRequestDTO dto) throws DataNotFoundException {
        return studentRegisterService.saveStudentRegister(dto);
    }

    @GetMapping(path = "/students/register/all/{id}")
    @PostAuthorize("hasAnyAuthority('USER')")
    public List<StudentResponseDTO> listStudent(@PathVariable("id") Long id){
        return studentRegisterService.listAllStudentRegisterByAY(id);
    }

    @GetMapping(path = "/students/register/{registerId}/academicyear/{ayId}")
    @PostAuthorize("hasAnyAuthority('USER')")
    public StudentResponseDTO getStudent(@PathVariable("registerId") Long registerId, @PathVariable("ayId") Long ayId){
        return studentRegisterService.getStudentByIdAndAcademicYear(registerId,ayId);
    }

    @GetMapping(path = "/students/{studentId}/academicyear/{ayId}")
    @PostAuthorize("hasAnyAuthority('USER')")
    public StudentResponseDTO getStudentByStudentIdAndAcademicYearId(@PathVariable("studentId") Long studentId, @PathVariable("ayId") Long ayId) throws DataNotFoundException {
        return studentRegisterService.getGradeRegistryByStudentIdAndAcademicYear(studentId,ayId);
    }

    @PutMapping(path = "/students/register/{registerId}")
    @PostAuthorize("hasAnyAuthority('USER')")
    public StudentResponseDTO updateStudent(@PathVariable("registerId") Long registerId, @RequestBody StudentResponseDTO register) throws DataNotFoundException {
        return StudentResponseDTO.fromDto(
                studentRegisterService.updateStudentRegister(registerId,register)
        );
    }



}
