package com.growup.ecountry.controller;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.growup.ecountry.config.TokenProvider;
import com.growup.ecountry.dto.ApiResponseDTO;
import com.growup.ecountry.dto.CountryDTO;
import com.growup.ecountry.dto.TokenDTO;
import com.growup.ecountry.service.CountryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.lang.model.type.NullType;
import java.util.List;

@RestController
@RequestMapping("/api/country")
@RequiredArgsConstructor
public class CountryController {
    private final CountryService countryService;
    private final TokenProvider jwt;

    //국가정보 조회
    @GetMapping("/{countryId}")
    public ResponseEntity<ApiResponseDTO<CountryData>> findAllCountries(@PathVariable Long countryId){
        try {
            ApiResponseDTO<CountryDTO> apiData = countryService.findCountries(countryId);
            CountryDTO countryDTO = apiData.getResult();
            return ResponseEntity.ok(new ApiResponseDTO<>(apiData.getSuccess(),apiData.getMessage(), new CountryData(countryDTO.getName(), countryDTO.getGrade(), countryDTO.getClassroom(), countryDTO.getSalaryDate(), countryDTO.getSchool(), countryDTO.getTreasury())));
        } catch (Exception e) {
            return ResponseEntity.ok(new ApiResponseDTO<>(false,"국가정보 조회 실패",null));
        }
    }

    //국가생성
     @PostMapping
     public ResponseEntity<ApiResponseDTO<CountryData2>> create(@RequestHeader(value = "Authorization") String token, @RequestBody CountryDTO countryDTO){
        try {
            TokenDTO authToken = jwt.validateToken(token);
            if(authToken.getId() != 0) {
                ApiResponseDTO<Long> apiData = countryService.create(countryDTO, authToken.getId());
                return ResponseEntity.ok(new ApiResponseDTO<>(apiData.getSuccess(), apiData.getMessage(),  new CountryData2(apiData.getResult())));
            }
            else {
                return ResponseEntity.ok(new ApiResponseDTO<>(false,"국가 생성 실패",null));
            }
        } catch (Exception e) {
            return ResponseEntity.ok(new ApiResponseDTO<>(false,"국가 생성 실패",null));
        }
     }

    //국가삭제
     @DeleteMapping("/{id}")
     public ResponseEntity<ApiResponseDTO<NullType>> delete(@PathVariable Long id){
        try{
            return ResponseEntity.ok(countryService.delete(id));
        } catch (Exception e) {
            return ResponseEntity.ok(new ApiResponseDTO<>(false,"국가 삭제 실패",null));
        }
    }

    static class CountryData {
        @JsonProperty
        private final String name;
        @JsonProperty
        private final Integer grade;
        @JsonProperty
        private final Integer classroom;
        @JsonProperty
        private final Integer salaryDate;
        @JsonProperty
        private final String school;
        @JsonProperty
        private final Integer treasury;
        public CountryData(String name, Integer grade, Integer classroom, Integer salaryDate, String school, Integer treasury) {
            this.name = name;
            this.grade = grade;
            this.classroom = classroom;
            this.salaryDate = salaryDate;
            this.school = school;
            this.treasury = treasury;
        }
    }
    static class CountryData2 {
        @JsonProperty
        private final Long id;
        public CountryData2(Long id) {
            this.id = id;
        }
    }
}
