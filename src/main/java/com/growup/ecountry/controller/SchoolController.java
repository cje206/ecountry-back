package com.growup.ecountry.controller;

import com.growup.ecountry.dto.ApiResponseDTO;
import com.growup.ecountry.dto.CountryDTO;
import com.growup.ecountry.service.CountryService;
import com.growup.ecountry.service.SchoolService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.lang.model.type.NullType;

@RestController
@RequestMapping("/api/school")
@RequiredArgsConstructor
public class SchoolController {
    private final CountryService countryService;
    private  final SchoolService schoolService;

    //학교 정보 조회
    @GetMapping
    public ResponseEntity<ApiResponseDTO<?>> getSchoolInfo(@RequestParam String schoolName){
        return ResponseEntity.ok(new ApiResponseDTO<>(true, "",schoolService.schoolInfoSearchAPI(schoolName)));
    }

    //급식정보 조회
//    @GetMapping("/menu/{countryId}")
//    public ResponseEntity<ApiResponseDTO<?>> getSchoolMenu(@PathVariable Long countryId){
//        try {
//            CountryDTO countryDTO = countryService.findCountryInfo(countryId);
//            if(countryDTO == null){
//                return ResponseEntity.ok(new ApiResponseDTO<NullType>(false, "국가 정보 조회에 실패하였습니다."));
//            }
//            //학교 정보 조회
//            //인증키
//            //f48ad57421754222bb1c099c1f296488
//        }catch (Exception e){
//            System.out.println("급식정보 조회 오류 : " + e.getMessage());
//            return ResponseEntity.ok(new ApiResponseDTO<NullType>(false, "급식 정보 조회에 실패하였습니다."));
//        }
//    }
}
