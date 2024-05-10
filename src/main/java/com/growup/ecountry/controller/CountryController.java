package com.growup.ecountry.controller;

import com.growup.ecountry.dto.CountryDTO;
import com.growup.ecountry.dto.ResponseDTO;
import com.growup.ecountry.service.CountryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/country")
@RequiredArgsConstructor
public class CountryController {
    private final CountryService countryService;
    //국가정보
    @GetMapping("/{country_id}")
    public ResponseEntity<CountryDTO> countryData(@PathVariable Long country_id){
        return ResponseEntity.ok(countryService.countryDataService(country_id));
    }
    //국가생성
    // ResponseDTO: 새 응답 DTO 필요
    // @PostMapping
    // public ResponseEntity<ResponseDTO> create(@RequestHeader String token, @RequestBody CountryDTO countryDTO){
    //
    // }

    //국가리스트조회
    // @GetMapping
    // public ResponseEntity<CountryDTO> searchList(String token){
    // }

    //국가삭제
    // @DeleteMapping
    // public ResponseEntity<ResponseDTO> searchList(String token){
    // }
}
