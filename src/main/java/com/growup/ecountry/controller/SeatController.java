package com.growup.ecountry.controller;

import com.growup.ecountry.dto.ApiResponseDTO;
import com.growup.ecountry.dto.SeatDTO;
import com.growup.ecountry.service.SeatService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.lang.model.type.NullType;
import java.util.List;

@RestController
@RequestMapping("/api/seat")
@RequiredArgsConstructor
public class SeatController {
    private final SeatService seatService;

    @PostMapping
    public ResponseEntity<ApiResponseDTO<NullType>> setSeat(@RequestBody List<SeatDTO> seatDTOS) {
        try {
            seatService.createSeat(seatDTOS);
            return ResponseEntity.ok(new ApiResponseDTO<>(true, "자리 배치 등록 완료"));
            
        } catch (Exception e) {
            return ResponseEntity.ok(new ApiResponseDTO<>(false, "자리 배치 등록 실패"));
            
        }
    }

    @GetMapping("/{countryId}")
    public ResponseEntity<ApiResponseDTO<List<SeatDTO>>> getSeat(@PathVariable Long countryId) {
        try {
            List<SeatDTO> result = seatService.getSeat(countryId);
            return ResponseEntity.ok(new ApiResponseDTO<>(true, "자리 배치 정보 조회 완료", result));

        } catch (Exception e) {
            return ResponseEntity.ok(new ApiResponseDTO<>(false, "자리 배치 정보 조회 실패"));

        }
    }

    @PostMapping("/update")
    public ResponseEntity<ApiResponseDTO<NullType>> updateSeat(@RequestBody List<SeatDTO> seatDTOS) {
        try {
            seatService.updateSeat(seatDTOS);
            return ResponseEntity.ok(new ApiResponseDTO<>(true, "자리 배치 수정 완료"));
        } catch (Exception e) {
            return ResponseEntity.ok(new ApiResponseDTO<>(false, e.getMessage()));
        }
    }
}
