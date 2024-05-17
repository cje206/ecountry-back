package com.growup.ecountry.service;

import com.growup.ecountry.dto.ApiResponseDTO;
import com.growup.ecountry.dto.SeatStatusDTO;
import com.growup.ecountry.entity.Countries;
import com.growup.ecountry.entity.Seats;
import com.growup.ecountry.entity.Students;
import com.growup.ecountry.repository.CountryRepository;
import com.growup.ecountry.repository.SeatRepository;
import com.growup.ecountry.repository.SeatStatusRepository;
import com.growup.ecountry.repository.StudentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.lang.model.type.NullType;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SeatStatusService {
    private final CountryRepository countryRepository;
    private final StudentRepository studentRepository;
    private final SeatRepository seatRepository;
    private final SeatStatusRepository seatStatusRepository;
    //학생 자리 사용 등록
//    public ApiResponseDTO<NullType> setSeatStatus(Long countryId, List<SeatStatusDTO> seatStatusDTOs) {
//        Countries countries = countryRepository.findById(countryId).orElseThrow(() -> new IllegalArgumentException("존재하지 않는 국가입니다"));
//        for(SeatStatusDTO seatStatus : seatStatusDTOs) {
//            if(seatStatus.getOwnerId() != null && seatStatus.getStudentId() != null ) {
//                Students ownerStudent = studentRepository.findByIdANDCountryId(seatStatus.getOwnerId(),countries.getId()).orElseThrow(() -> new IllegalArgumentException("존재하지 않는 학생입니다."));
//                Students student = studentRepository.findByIdANDCountryId(seatStatus.getStudentId(),countries.getId()).orElseThrow(() -> new IllegalArgumentException("존재하지 않는 학생입니다."));
////                Seats seat = seatRepository.findByStudentIdAndSeatNumber(student.getId(), seatStatus.getSeatNumber()).orElseThrow(() -> new IllegalArgumentException("존재하지 않는 군트입니다."));
//            }
//            else if(seatStatus.getOwnerId() != null && seatStatus.getStudentId() == null) {
//                Students ownerStudent = studentRepository.findByIdANDCountryId(seatStatus.getOwnerId(),countries.getId()).orElseThrow(() -> new IllegalArgumentException("존재하지 않는 학생입니다."));
//            }
//            else if(seatStatus.getOwnerId() == null && seatStatus.getStudentId() != null) {
//                Students student = studentRepository.findByIdANDCountryId(seatStatus.getStudentId(),countries.getId()).orElseThrow(() -> new IllegalArgumentException("존재하지 않는 학생입니다."));
//            }
//            else if(seatStatus.getOwnerId() == null && seatStatus.getStudentId() == null){
//
//            }
//        }
//        return null;
//    }
}
