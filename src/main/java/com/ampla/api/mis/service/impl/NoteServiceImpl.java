package com.ampla.api.mis.service.impl;

import com.ampla.api.exception.DataNotFoundException;
import com.ampla.api.mis.dto.NoteRequestDTO;
import com.ampla.api.mis.dto.NoteResponseDTO;
import com.ampla.api.mis.entities.*;
import com.ampla.api.mis.repository.NoteRepository;
import com.ampla.api.mis.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class NoteServiceImpl implements NoteService {

    private final NoteRepository noteRepository;

    private final GradeService gradeService;

    private final StudentService studentService;

    private final CourseService courseService;

    private final EmployeeService employeeService;

    private final AcademicYearService academicYearService;

    @Autowired
    public NoteServiceImpl
            (NoteRepository noteRepository,
             GradeService gradeService,
             StudentService studentService,
             CourseService courseService,
             EmployeeService employeeService,
             AcademicYearService academicYearService
            ) {
        this.noteRepository = noteRepository;
        this.gradeService = gradeService;
        this.studentService = studentService;
        this.courseService = courseService;
        this.employeeService = employeeService;
        this.academicYearService = academicYearService;
    }

    @Override
    public NoteResponseDTO saveNote(NoteRequestDTO dto) throws DataNotFoundException {
        Note note = new Note();
        if (!dto.equals(null)) {
            if (dto.getGradeId() != null) {
                Grade grade = gradeService.getGradeById(dto.getGradeId());
                note.setGrade(grade);
            }
            if(dto.getCourseID() != null){
                Course course = courseService.getCourseById(dto.getCourseID());
                note.setCourse(course);
            }
            if(dto.getEmployeeId() != null){
                Employee employee = employeeService.getEmployeeById(dto.getEmployeeId())
                        .orElseThrow(()->new DataNotFoundException("Employee with ID "+dto.getEmployeeId()+" not found"));
                note.setEmployee(employee);
            }
            if(dto.getStudentId() != null){
                Student student = studentService.getOneStudent(dto.getStudentId());
                note.setStudent(student);
            }
            if(dto.getAcademicYearId() != null){
                AcademicYear academicYear = academicYearService.getById(dto.getAcademicYearId());
            }
            if(dto.getPoint() != null){
                note.setPoint(dto.getPoint());
            }
        }

        return NoteResponseDTO.toDto(noteRepository.save(note));
    }

    @Override
    public NoteResponseDTO findNoteById(Long id) throws DataNotFoundException {
        Note note = noteRepository.findById(id).orElseThrow(() -> new DataNotFoundException("note with ID " + id + " not found"));
        return NoteResponseDTO.toDto(note);
    }

    @Override
    public List<NoteResponseDTO> findAllByStudentIdAndAcademicYearId(Long studentId, Long academicYearId) throws DataNotFoundException {
        List<Note> notes = Optional.of(noteRepository.findAllByStudentIdAndAcademicYearId(studentId, academicYearId))
                .orElseThrow(() -> new DataNotFoundException("Notes for student '" + studentId + "' and academic year '" + academicYearId + "' not found"));

        return notes.stream().map(NoteResponseDTO::toDto).collect(Collectors.toList());
    }


    @Override
    public List<NoteResponseDTO> findAllByAcademicYear(Long id) throws DataNotFoundException {
        List<Note> notes = Optional.of(noteRepository.findAllByAcademicYearId(id))
                .orElseThrow(() -> new DataNotFoundException("no notes for academic year " + id));
        return notes.stream().map(NoteResponseDTO::toDto).collect(Collectors.toList());
    }

    @Override
    public List<NoteResponseDTO> findAllByAcademicYearAndByGradeAndCourse(Long academicYearId, Long gradeId, Long courseId) throws DataNotFoundException {
        List<Note> notes = Optional.of(noteRepository.findAllByAcademicYearIdAndCourseIdAndGradeId(academicYearId,courseId,gradeId))
                .orElseThrow(()-> new DataNotFoundException("Notes not found"));

        return notes.stream().map(NoteResponseDTO::toDto).collect(Collectors.toList());
    }

    @Override
    public List<NoteResponseDTO> findAllByAcademicYearAndByGradeAndStudent(Long academicYearId, Long gradeId, Long studentId) throws DataNotFoundException {
       List<Note> notes = Optional.of(noteRepository.findAllByAcademicYearIdAndGradeIdAndStudentId(academicYearId,gradeId,studentId))
               .orElseThrow(()->new DataNotFoundException("Notes not found"));

        return notes.stream().map(NoteResponseDTO::toDto).collect(Collectors.toList());
    }

 }
