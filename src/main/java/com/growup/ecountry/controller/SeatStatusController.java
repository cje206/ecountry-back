package com.growup.ecountry.controller;

import com.growup.ecountry.dto.ApiResponseDTO;
import com.growup.ecountry.dto.SeatStatusDTO;
import com.growup.ecountry.service.SeatStatusService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.lang.model.type.NullType;
import java.util.List;

@RestController
@RequestMapping("/api/seat/status")
@RequiredArgsConstructor
public class SeatStatusController {
    private final SeatStatusService seatStatusService;

    //학생 자리 사용 등록
//    @PostMapping("/{countryId}")
//    public ResponseEntity<ApiResponseDTO<NullType>> setSeatStatus(@PathVariable Long countryId, @RequestBody List<SeatStatusDTO> seatStatusDTO) {
//        seatStatusService.setSeatStatus(countryId, seatStatusDTO);
//        return null;
//    }
}
