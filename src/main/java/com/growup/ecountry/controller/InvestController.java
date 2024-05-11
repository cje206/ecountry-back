package com.growup.ecountry.controller;

import com.growup.ecountry.dto.ApiResponseDTO;
import com.growup.ecountry.dto.InvestDTO;
import com.growup.ecountry.dto.InvestStatusDTO;
import com.growup.ecountry.service.InvestService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.lang.model.type.NullType;
import java.util.List;

@RestController
@RequestMapping("/api/invest")
@RequiredArgsConstructor
public class InvestController {
    private final InvestService investService;

//    투자 항목
    @PostMapping
    public ResponseEntity<ApiResponseDTO<NullType>> setInvest(@RequestBody List<InvestDTO> investDTOs) {
        investService.createInvest(investDTOs);
        return ResponseEntity.ok(new ApiResponseDTO<NullType>(true, "투자 항목 생성 완료", null));
    }

    @GetMapping("/{countryId}")
    public ResponseEntity<ApiResponseDTO<List<InvestDTO>>> getInvest(@PathVariable Long countryId) {
        return ResponseEntity.ok(new ApiResponseDTO<List<InvestDTO>>(true,"투자 항목 조회 완료",investService.getInvest(countryId)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponseDTO<NullType>> deleteInvest(@PathVariable Long id) {
        investService.deleteInvest(id);
        return ResponseEntity.ok(new ApiResponseDTO<NullType>(true, "투자 항목 삭제 완료", null));
    }

    @PatchMapping
    public ResponseEntity<ApiResponseDTO<NullType>> updateInfo(@RequestBody InvestDTO investDTO) {
        investService.updateInfo(investDTO);
        return ResponseEntity.ok(new ApiResponseDTO<>(true, "투자 정보 업데이트 완료", null));
    }

//    투자 현황
    @GetMapping("/status/{investId}")
    public ResponseEntity<ApiResponseDTO<List<InvestStatusDTO>>> getStatus(@PathVariable Long investId) {
        return ResponseEntity.ok(new ApiResponseDTO<List<InvestStatusDTO>>(true, "투자 현황 조회 완료", investService.getStatus(investId)));
    }

    @PostMapping("/status")
    public ResponseEntity<ApiResponseDTO<NullType>> addStatus(@RequestBody InvestStatusDTO investStatusDTO) {
        investService.addStatus(investStatusDTO);
        return ResponseEntity.ok(new ApiResponseDTO<NullType>(true, "투자 정보 추가 완료", null));
    }
    
    @DeleteMapping("/status/{id}")
    public ResponseEntity<ApiResponseDTO<NullType>> deleteStatus(@PathVariable Long id) {
        investService.deleteStatus(id);
        return ResponseEntity.ok(new ApiResponseDTO<NullType>(true, "투자 정보 삭제 완료", null));
    }

}
