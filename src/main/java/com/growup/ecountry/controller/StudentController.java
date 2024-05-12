package com.growup.ecountry.controller;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.growup.ecountry.dto.ApiResponseDTO;
import com.growup.ecountry.dto.StudentDTO;
import com.growup.ecountry.service.StudentService;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.lang.model.type.NullType;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/student")
@RequiredArgsConstructor
public class StudentController {
    private final StudentService studentService;

    //국민등록(수기)
    @PostMapping("/{countryId}")
    public ResponseEntity<ApiResponseDTO<NullType>> studentAdd(@PathVariable Long countryId, @RequestBody List<StudentDTO> studentDTOS){
        return ResponseEntity.ok(studentService.studentAdd(countryId,studentDTOS));
    }
    //국민등록(엑셀)
    @PostMapping("add/{countryId}")
    public ResponseEntity<ApiResponseDTO<NullType>> studentAddExcel(@PathVariable Long countryId, @RequestParam("file")MultipartFile file) throws IOException {
        return ResponseEntity.ok(studentService.studentAddExcel(countryId,file));
    }

    //국민조회
    @GetMapping("/{countryId}")
    public ResponseEntity<ApiResponseDTO<List<StudentData>>> studentList(@PathVariable Long countryId){
        List<StudentData> studentDataList = new ArrayList<>();
        ApiResponseDTO<List<StudentDTO>> apiData = studentService.studentList(countryId);
        List<StudentDTO> students = apiData.getResult();
        for(StudentDTO student : students) {
            StudentData studentData = new StudentData(student.getId(), student.getName(), student.getRollNumber(), student.getRating());
            studentDataList.add(studentData);
        }
        return ResponseEntity.ok(new ApiResponseDTO<>(apiData.getSuccess(), apiData.getMessage(),studentDataList));
    }
    //국민삭제
    @DeleteMapping("/{countryId}")
    public ResponseEntity<ApiResponseDTO<NullType>> studentDelete(@PathVariable Long countryId,@RequestBody StudentDTO studentDTO){
        return ResponseEntity.ok(studentService.studentDelete(countryId,studentDTO.getId()));
    }
    //국민수정
    @PatchMapping("/{countryId}")
    public ResponseEntity<ApiResponseDTO<NullType>> studentUpdate(@PathVariable Long countryId,@RequestBody StudentDTO studentDTO){
        return ResponseEntity.ok(studentService.studentUpdate(countryId,studentDTO));
    }


    static class StudentData {
        @JsonProperty
        private final Long id;
        @JsonProperty
        private final String name;
        @JsonProperty
        private final Integer rollNumber;
        @JsonProperty
        private final Integer rating;
        public StudentData(Long id, String name, Integer rollNumber, Integer rating) {
            this.id = id;
            this.name = name;
            this.rollNumber = rollNumber;
            this.rating = rating;
        }
    }
}
