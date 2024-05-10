package com.growup.ecountry.service;

import com.growup.ecountry.dto.CountryDTO;
import com.growup.ecountry.dto.ResponseDTO;
import com.growup.ecountry.entity.Countries;
import com.growup.ecountry.repository.CountryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CountryService {
    private final CountryRepository countryRepository;

    //국가정보
    public CountryDTO countryDataService(Long country_id){
        Optional<Countries> countryExist = countryRepository.findById(country_id);
        if(countryExist.isPresent()){
            Countries countries = countryExist.get();
            CountryDTO countryDTO =
                    CountryDTO.builder()
                            .school(countries.getSchool())
                            .name(countries.getName())
                            .grade(countries.getGrade())
                            .classroom(countries.getClassroom())
                            .unit(countries.getUnit())
                            .treasury(countries.getTreasury())
                            .salaryDate(countries.getSalaryDate()).build();
            return countryDTO;
        } else {
            return new CountryDTO();
        }
    }
    // 아래 목록 전부 jwt 토큰을 id로 변환하는 로직 및 새 ResponseDTO 클래스 필요

    //국가생성
    // ResponseDTO: 새 응답 DTO 필요
    // public ResponseDTO create(String token, CountryDTO countryDTO){
    //  }

    //국가 리스트 조회

    // ResponseDTO: 새 응답 DTO 필요
    // public CountryDTO searchList(String token){
    // }

    //국가 삭제(얘는 DTO 불필요)
    //  public ResponseDTO delete(String token){
    //  }
}
