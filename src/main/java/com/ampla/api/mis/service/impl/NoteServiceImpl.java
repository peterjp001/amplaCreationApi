package com.ampla.api.mis.service.impl;

import com.ampla.api.exception.DataNotFoundException;
import com.ampla.api.mis.dto.NoteRequestDTO;
import com.ampla.api.mis.dto.NoteResponseDTO;
import com.ampla.api.mis.entities.*;
import com.ampla.api.mis.repository.NoteRepository;
import com.ampla.api.mis.service.*;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
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
            if (dto.getCourseID() != null) {
                Course course = courseService.getCourseById(dto.getCourseID());
                note.setCourse(course);
            }
            if (dto.getEmployeeId() != null) {
                Employee employee = employeeService.getEmployeeById(dto.getEmployeeId())
                        .orElseThrow(() -> new DataNotFoundException("Employee with ID " + dto.getEmployeeId() + " not found"));
                note.setEmployee(employee);
            }
            if (dto.getStudentId() != null) {
                Student student = studentService.getOneStudent(dto.getStudentId());
                note.setStudent(student);
            }
            if (dto.getAcademicYearId() != null) {
                AcademicYear academicYear = academicYearService.getById(dto.getAcademicYearId());
                note.setAcademicYear(academicYear);
            }
            if (dto.getPoint() != null) {
                note.setPoint(dto.getPoint());
            }
        }

        return NoteResponseDTO.toDto(noteRepository.save(note));
    }

    @Override
    public Note findExistingNoteWithAllInformations(Long studentId, Long ayId, Long courseId, Long gradeId, Long employeeId) {
        return noteRepository.findByStudentIdAndAcademicYearIdAndCourseIdAndGradeIdAndEmployeeId
                (studentId, ayId, courseId, gradeId, employeeId);
    }

    @Override
    public List<NoteResponseDTO> readNotesFile(InputStream file, Long academicYearId) throws DataNotFoundException {

        List<Note> notes = new ArrayList<>();
        AcademicYear academicYear = academicYearService.getById(academicYearId);
        Grade grade = new Grade();
        Course course = new Course();
        Employee employee = new Employee();
        Student student;

        String lastName = null;
        String firstname = null;

        String studentLastname = null;
        String studentFirstName = null;
        Float point = null;

        try {
            // Create Workbook instance holding reference to
            // .xlsx file
            XSSFWorkbook workbook = new XSSFWorkbook(file);

            // Get first/desired sheet from the workbook
            XSSFSheet sheet = workbook.getSheetAt(0);

            // Iterate through each rows one by one
            Iterator<Row> rowIterator = sheet.iterator();
            // Till there is an element condition holds true
            while (rowIterator.hasNext()) {

                Row row = rowIterator.next();
                // For each row, iterate through all the
                // columns
                Iterator<Cell> cellIterator = row.cellIterator();

                while (cellIterator.hasNext()) {

                    Cell cell = cellIterator.next();

                    if(row.getRowNum() == 5){
                        if(cell.getColumnIndex() == 2){
                            course = courseService.getCourseByName(cell.getStringCellValue());
                        }
                    }

                    if(row.getRowNum() == 6){
                        if(cell.getColumnIndex() == 2){
                            grade = gradeService.getGradeByGradeName(cell.getStringCellValue());
                        }
                    }

                    if(row.getRowNum() == 6){
                        if(cell.getColumnIndex() == 5){
                            lastName = cell.getStringCellValue();
                        }
                        if(cell.getColumnIndex() == 6){
                            firstname = cell.getStringCellValue();
                        }
                        if(firstname != null && lastName != null){
                            employee = employeeService.getByFirstNameAndLastName(firstname,lastName);
                        }
                    }

                    if(cell.getRowIndex() > 11){
                        if(cell.getColumnIndex() >1 && cell.getColumnIndex() <=4){

                            if(studentFirstName != null && studentLastname != null && point != null){
                                studentLastname = null;
                                studentFirstName = null;
                                point = null;
                            }

                            if(cell.getColumnIndex() == 2){
                                studentLastname   = cell.getStringCellValue();
                            }
                            if(cell.getColumnIndex() == 3){
                                studentFirstName  = cell.getStringCellValue();
                            }
                            if(cell.getColumnIndex() == 4){
                                point = (float) cell.getNumericCellValue();
                            }

                            if(studentFirstName != null && studentLastname != null && point != null){
//                                System.out.println(studentLastname+" "+studentFirstName);
//                                System.out.println("**************************************");
//                                System.out.println("Academic Year: "+ academicYear.getId());
//                                System.out.println("Professeur: "+employee.getCodeEmployee());
//                                System.out.println("Classe: "+grade.getGradeName());
//                                System.out.println("Cours: "+ course.getCourseName());
//                                System.out.println("Eleve: "+ student.getFirstName()+" "+student.getLastName());
//                                System.out.println("Point: "+ point);
//                                System.out.println("**************************************\n");

                                student = studentService.getByFirstNameAndLastName(studentFirstName,studentLastname);

                                Note verifyNote = this.findExistingNoteWithAllInformations
                                                    (student.getId(),academicYear.getId(), course.getId(), grade.getId(), employee.getId());

                                if(verifyNote == null){
                                    Note note = new Note();
                                    note.setPoint(point);
                                    note.setGrade(grade);
                                    note.setCourse(course);
                                    note.setStudent(student);
                                    note.setAcademicYear(academicYear);
                                    note.setEmployee(employee);
                                    notes.add(note);
                                }else{
                                    verifyNote.setPoint(point);
                                    notes.add(verifyNote);
                                }

                            }
                        }
                    }
                }
            }
            // Closing file output streams
            file.close();
        }

        // Catch block to handle exceptions
        catch (Exception e) {
            e.printStackTrace();
        }

//        return notes.stream()
//                .map(NoteResponseDTO::toDto)
//                .collect(Collectors.toList());

        return noteRepository.saveAll(notes)
                .stream()
                .map(NoteResponseDTO::toDto)
                .collect(Collectors.toList());
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
        List<Note> notes = Optional.of(noteRepository.findAllByAcademicYearIdAndCourseIdAndGradeId(academicYearId, courseId, gradeId))
                .orElseThrow(() -> new DataNotFoundException("Notes not found"));

        return notes.stream().map(NoteResponseDTO::toDto).collect(Collectors.toList());
    }

    @Override
    public List<NoteResponseDTO> findAllByAcademicYearAndByGradeAndStudent(Long academicYearId, Long gradeId, Long studentId) throws DataNotFoundException {
        List<Note> notes = Optional.of(noteRepository.findAllByAcademicYearIdAndGradeIdAndStudentId(academicYearId, gradeId, studentId))
                .orElseThrow(() -> new DataNotFoundException("Notes not found"));

        return notes.stream().map(NoteResponseDTO::toDto).collect(Collectors.toList());
    }

}
