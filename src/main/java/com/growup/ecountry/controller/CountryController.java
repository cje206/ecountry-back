package com.growup.ecountry.controller;

import com.growup.ecountry.config.TokenProvider;
import com.growup.ecountry.dto.ApiResponseDTO;
import com.growup.ecountry.dto.CountryDTO;
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
     public ResponseEntity<ApiResponseDTO<NullType>> create(@RequestHeader(value = "Authorization") String token, @RequestBody CountryDTO countryDTO){
         String authToken = jwt.validateToken(token);
         if(authToken != "false") {
            return ResponseEntity.ok(countryService.create(countryDTO,authToken));
         }
         else {
            return ResponseEntity.ok(new ApiResponseDTO<>(false,"국가 생성 실패",null));
         }
     }
    //국가리스트조회
     @GetMapping
     public ResponseEntity<ApiResponseDTO<List<CountryDTO>>> findCountryList(@RequestHeader(value = "Authorization") String token){
        return ResponseEntity.ok(countryService.findCountryList(token));
     }

    //국가삭제
     @DeleteMapping("/{id}")
     public ResponseEntity<ApiResponseDTO<NullType>> delete(@PathVariable Long id){
        return ResponseEntity.ok(countryService.delete(id));
    }
}
