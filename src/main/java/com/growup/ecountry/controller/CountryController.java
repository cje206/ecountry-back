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
    public ResponseEntity<ApiResponseDTO<CountryDTO>> findAllCountries(@PathVariable Long countryId){
        return ResponseEntity.ok(countryService.findCountries(countryId));
    }

    //국가생성
     @PostMapping
     public ResponseEntity<ApiResponseDTO<Long>> create(@RequestHeader(value = "Authorization") String token, @RequestBody CountryDTO countryDTO){
         TokenDTO authToken = jwt.validateToken(token);
         if(authToken.getId() != 0) {
             ApiResponseDTO<Long> apiData = countryService.create(countryDTO, authToken.getId());
             CountryData countryData = new CountryData(apiData.getResult());
            return ResponseEntity.ok(new ApiResponseDTO<>(apiData.getSuccess(), apiData.getMessage(), countryData.countryId));
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

    static class CountryData {
        @JsonProperty
        private final Long countryId;
        public CountryData(Long countryId) {
            this.countryId = countryId;
        }
    }
}
