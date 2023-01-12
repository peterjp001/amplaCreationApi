package com.ampla.api.mis.dto;


import com.ampla.api.mis.entities.AcademicYear;
import com.ampla.api.mis.entities.Note;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class NoteResponseDTO {

    private Long noteId;

    private Float point;

    private Long gradeId;
    private String gradeName;

    private Long courseID;
    private String courseName;

    private Long employeeId;
    private String employeeFirstName;
    private String employeeLastName;
    private String codeEmployee;

    private Long studentId;
    private String studentFirstName;
    private String studentLastName;
    private String studentCode;

    private Long academicYearId;
    private String academicYear;

    public static NoteResponseDTO toDto(Note note){

        String ay = note.getAcademicYear().getDateStart().toString().split("-")[0]
                + '-'+note.getAcademicYear().getDateEnd().toString().split("-")[0];

        return NoteResponseDTO.builder()
                .noteId(note.getId())
                .point(note.getPoint())
                .gradeId(note.getGrade().getId())
                .gradeName(note.getGrade().getGradeName())
                .courseID(note.getCourse().getId())
                .courseName(note.getCourse().getCourseName())
                .employeeId(note.getEmployee().getId())
                .codeEmployee(note.getEmployee().getCodeEmployee())
                .employeeFirstName(note.getEmployee().getFirstName())
                .employeeLastName(note.getEmployee().getLastName())
                .studentId(note.getStudent().getId())
                .studentCode(note.getStudent().getCodeStudent())
                .studentFirstName(note.getStudent().getFirstName())
                .studentLastName(note.getStudent().getLastName())
                .academicYearId(note.getAcademicYear().getId())
                .academicYear(ay)
                .build();
    }

}
