package com.growup.ecountry.controller;

import com.growup.ecountry.config.TokenProvider;
import com.growup.ecountry.dto.ApiResponseDTO;
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
    private final TokenProvider jwt;

    //국가정보
    @GetMapping("/{country_id}")
    public ResponseEntity<CountryDTO> countryData(@PathVariable Long country_id){
        return ResponseEntity.ok(countryService.countryDataService(country_id));
    }
//    국가생성(token : id로 넣어서 보내주기)
//     ResponseDTO: 새 응답 DTO 필요
     @PostMapping
     public ResponseEntity<ApiResponseDTO> create(@RequestHeader(value = "Authorization") String token, @RequestBody CountryDTO countryDTO){
        System.out.print(token);
         String authToken = jwt.validateToken(token);
         if(authToken != "false") {
            return ResponseEntity.ok(countryService.create(countryDTO,authToken));

         }
         else {
            return ResponseEntity.ok(new ApiResponseDTO(false,"국가 생성 실패",""));
         }
     }
    //국가리스트조회
    // @GetMapping
    // public ResponseEntity<CountryDTO> searchList(String token){
    // }

    //국가삭제
    // @DeleteMapping
    // public ResponseEntity<ResponseDTO> searchList(String token){
    // }
}
