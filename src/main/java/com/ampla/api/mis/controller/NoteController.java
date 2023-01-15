package com.ampla.api.mis.controller;


import com.ampla.api.exception.DataNotFoundException;
import com.ampla.api.mis.dto.NoteRequestDTO;
import com.ampla.api.mis.dto.NoteResponseDTO;
import com.ampla.api.mis.service.NoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
public class NoteController {

    private final NoteService noteService;

    @Autowired
    public NoteController(NoteService noteService) {
        this.noteService = noteService;
    }

    @PostMapping(path="/send/notes", consumes = {"multipart/form-data"})
    @PostAuthorize("hasAnyAuthority('USER')")
    public List<NoteResponseDTO> sendSinistre(@RequestParam("academicYearId") Long academicYearId,@RequestParam("attachment") MultipartFile attachment ) throws IOException, DataNotFoundException {
        return noteService.readNotesFile(attachment.getInputStream(),academicYearId);
    }

    @PostMapping(path = "/notes")
    @PostAuthorize("hasAnyAuthority('USER')")
    public NoteResponseDTO newNotes(@RequestBody NoteRequestDTO dto) throws DataNotFoundException {
        return noteService.saveNote(dto);
    }

    @GetMapping (path = "/notes/{noteId}")
    @PostAuthorize("hasAnyAuthority('USER')")
    public NoteResponseDTO getNote(@PathVariable Long noteId) throws DataNotFoundException {
        return noteService.findNoteById(noteId);
    }

    @GetMapping (path = "/notes/academicyear/{academicYearId}")
    @PostAuthorize("hasAnyAuthority('USER')")
    public List<NoteResponseDTO> getNotesByAy(@PathVariable Long academicYearId) throws DataNotFoundException {
        return noteService.findAllByAcademicYear(academicYearId);
    }

    @GetMapping (path = "/notes/student/{studentId}/academicyear/{academicYearId}")
    @PostAuthorize("hasAnyAuthority('USER')")
    public List<NoteResponseDTO> getNotesByStudentIdAndAy(@PathVariable Long academicYearId, @PathVariable Long studentId) throws DataNotFoundException {
        return noteService.findAllByStudentIdAndAcademicYearId(studentId,academicYearId);
    }

    @GetMapping (path = "/notes/academicyear/{academicYearId}/grade/{gradeId}/course/{courseId}")
    @PostAuthorize("hasAnyAuthority('USER')")
    public List<NoteResponseDTO> getNotesByAcademicYearAndByGradeAndCourse
            (@PathVariable Long academicYearId, @PathVariable Long gradeId, @PathVariable Long courseId) throws DataNotFoundException {
        return noteService.findAllByAcademicYearAndByGradeAndCourse(academicYearId,gradeId,courseId);
    }

    @GetMapping (path = "/notes/academicyear/{academicYearId}/grade/{gradeId}/student/{studentId}")
    @PostAuthorize("hasAnyAuthority('USER')")
    public List<NoteResponseDTO> findAllByAcademicYearAndByGradeAndStudent
            (@PathVariable Long academicYearId, @PathVariable Long gradeId, @PathVariable Long studentId) throws DataNotFoundException {
        return noteService.findAllByAcademicYearAndByGradeAndStudent(academicYearId,gradeId,studentId);
    }
}
