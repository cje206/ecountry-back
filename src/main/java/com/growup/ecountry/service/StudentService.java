package com.growup.ecountry.service;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.growup.ecountry.dto.ApiResponseDTO;
import com.growup.ecountry.dto.CountryDTO;
import com.growup.ecountry.dto.StudentDTO;
import com.growup.ecountry.entity.Countries;
import com.growup.ecountry.entity.Students;
import com.growup.ecountry.entity.Users;
import com.growup.ecountry.repository.CountryRepository;
import com.growup.ecountry.repository.StudentRepository;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.lang.model.type.NullType;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class StudentService {
    private final StudentRepository studentRepository;
    private final CountryRepository countryRepository;
    //국민등록(수기)
    public ApiResponseDTO<NullType> studentAdd(Long countryId, List<StudentDTO> students){
        Optional<Countries> countryExist = countryRepository.findById(countryId);
        if(countryExist.isPresent()){
            Countries countries = countryExist.get();
            for(StudentDTO student : students){
                Students studentEntity = Students.builder()
                        .name(student.getName())
                        .rollNumber(student.getRollNumber())
                        .pw(student.getPw())
                        .img(student.getImg())
                        .countryId(countries.getId()).build();
                studentRepository.save(studentEntity);
            }
            return new ApiResponseDTO<>(true,"국민등록 성공",null);
        }
        else {
            return new ApiResponseDTO<>(false,"국가가 존재하지 않습니다",null);
        }
    }
    //국민조회
    public ApiResponseDTO<List<StudentDTO>> studentList(Long countryId){
            List<Students> students = studentRepository.findAllByCountryId(countryId);
            List<StudentDTO> studentDTOList = new ArrayList<>();
            for(Students student : students){
                StudentDTO studentDTO = StudentDTO.builder()
                        .id(student.getId())
                        .name(student.getName())
                        .rollNumber(student.getRollNumber())
                        .rating(student.getRating())
                        .build();
                studentDTOList.add(studentDTO);
            }
            return new ApiResponseDTO<>(true,"국민조회 성공",studentDTOList);
    }
    //국민삭제
    public ApiResponseDTO<NullType> studentDelete(Long countryId,Long id){
        Optional<Students> studentExist = studentRepository.findByIdANDCountryId(id,countryId);
        if(studentExist.isPresent()){
            Students student = studentExist.get();
            studentRepository.deleteById(student.getId());
            return new ApiResponseDTO<>(true,"국민삭제 성공",null);
        }
        else {
            return new ApiResponseDTO<>(false,"국민이 존재하지 않습니다",null);
        }
    }
    //국민수정
    public ApiResponseDTO<NullType> studentUpdate(Long countryId,StudentDTO studentDTO){
        Students student = studentRepository.findByIdANDCountryId(studentDTO.getId(),countryId).orElseThrow(()->{
            throw new IllegalArgumentException("학생 정보 혹은 국가 아이디가 존재하지 않습니다");
        });
        student = Students.builder()
                    .id(studentDTO.getId())
                    .name(studentDTO.getName())
                    .rollNumber(studentDTO.getRollNumber())
                    .pw(studentDTO.getPw())
                    .rating(studentDTO.getRating())
                    .countryId(student.getCountryId()).build();
            studentRepository.save(student);
            return new ApiResponseDTO<>(true,"국민수정 성공",null);
        }
}
