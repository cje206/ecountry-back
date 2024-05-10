package com.growup.ecountry.controller;

import com.growup.ecountry.dto.ApiResponseDTO;
import com.growup.ecountry.dto.InvestDTO;
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

    @PostMapping
//    public ResponseEntity<ApiResponseDTO<NullType>> setInvest(@RequestBody List<InvestDTO> investDTOs) {
//        investService.create(investDTOs);
//        return ResponseEntity.ok(new ApiResponseDTO<NullType>(true, "투자 항목 생성 완료", null));
//    }

    @GetMapping("/{countryId}")
    public ResponseEntity<List<InvestDTO>> getInvest(@PathVariable Long countryId) {
        return ResponseEntity.ok(investService.getInvest(countryId));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteInvest(@PathVariable Long id) {
        investService.deleteInvest(id);
        return ResponseEntity.ok().build();
    }
}
