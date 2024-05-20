package com.growup.ecountry.service;

import com.growup.ecountry.dto.SeatDTO;
import com.growup.ecountry.entity.Seats;
import com.growup.ecountry.repository.SeatRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SeatService {
    private final SeatRepository seatRepository;

    public List<Seats> createSeat(List<SeatDTO> seatDTOS) {
        List<Seats> seats = seatDTOS.stream().map(seat -> Seats.builder().rowNum(seat.getRowNum())
                .colNum(seat.getColNum()).countryId(seat.getCountryId()).build()).toList();
        return seatRepository.saveAll(seats);
    }

    public List<SeatDTO> getSeat(Long countryId) {
        return seatRepository.findByCountryId(countryId).stream().map(seat -> SeatDTO.builder()
                .id(seat.getId()).rowNum(seat.getRowNum()).colNum(seat.getColNum())
                .countryId(seat.getCountryId()).build()).collect(Collectors.toList());
    }

    public List<Seats> updateSeat(List<SeatDTO> seatDTOS) {
        List<Seats> prevData = seatRepository.findByCountryId(seatDTOS.get(0).getCountryId());
        for (Seats data: prevData) {
            seatRepository.deleteById(data.getId());
        }
//        자리 배치 변경 시 해당 countryId를 가진 자리 사용 현황 table의 데이터 삭제
        List<Seats> seats = seatDTOS.stream().map(seat -> Seats.builder().rowNum(seat.getRowNum())
                .colNum(seat.getColNum()).countryId(seat.getCountryId()).build()).toList();
        return seatRepository.saveAll(seats);
    }
}
