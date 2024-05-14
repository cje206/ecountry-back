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
        ApiResponseDTO<CountryDTO> apiData = countryService.findCountries(countryId);
        CountryDTO countryDTO = apiData.getResult();
        return ResponseEntity.ok(new ApiResponseDTO<>(apiData.getSuccess(),apiData.getMessage(), new CountryData(countryDTO.getName(), countryDTO.getGrade(), countryDTO.getClassroom(), countryDTO.getSalaryDate(), countryDTO.getSchool(), countryDTO.getTreasury())));
    }

    //국가생성
     @PostMapping
     public ResponseEntity<ApiResponseDTO<CountryData2>> create(@RequestHeader(value = "Authorization") String token, @RequestBody CountryDTO countryDTO){
         TokenDTO authToken = jwt.validateToken(token);
         if(authToken.getId() != 0) {
             ApiResponseDTO<Long> apiData = countryService.create(countryDTO, authToken.getId());
            return ResponseEntity.ok(new ApiResponseDTO<>(apiData.getSuccess(), apiData.getMessage(),  new CountryData2(apiData.getResult())));
         }
         else {
            return ResponseEntity.ok(new ApiResponseDTO<>(false,"국가 생성 실패",null));
         }
     }

    //국가삭제
     @DeleteMapping("/{id}")
     public ResponseEntity<ApiResponseDTO<NullType>> delete(@PathVariable Long id){
        return ResponseEntity.ok(countryService.delete(id));
    }

    //국고 수정
    @PatchMapping("/treasury/{countryId}")
    public ResponseEntity<ApiResponseDTO<NullType>> updateTreasury(@PathVariable Long countryId,@RequestBody CountryDTO countryDTO){
        boolean success = countryService.updateTreasury(countryId, countryDTO.getTreasury());
        String msg = success ? "국고 수정에 성공하였습니다." : "국고 수정에 실패하였습니다.";
        return ResponseEntity.ok(new ApiResponseDTO<NullType>(success, msg));
    }

    static class CountryData {
        @JsonProperty
        private final String name;
        @JsonProperty
        private final Integer grade;
        @JsonProperty
        private final Integer classroom;
        @JsonProperty
        private final Integer salary_date;
        @JsonProperty
        private final String school;
        @JsonProperty
        private final Integer treasury;
        public CountryData(String name, Integer grade, Integer classroom, Integer salary_date, String school, Integer treasury) {
            this.name = name;
            this.grade = grade;
            this.classroom = classroom;
            this.salary_date = salary_date;
            this.school = school;
            this.treasury = treasury;
        }
    }
    static class CountryData2 {
        @JsonProperty
        private final Long country_id;
        public CountryData2(Long country_id) {
            this.country_id = country_id;
        }
    }
}
