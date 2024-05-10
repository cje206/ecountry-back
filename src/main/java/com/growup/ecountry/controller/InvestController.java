package com.growup.ecountry.controller;

import com.growup.ecountry.dto.InvestDTO;
import com.growup.ecountry.dto.UserDTO;
import com.growup.ecountry.entity.Invests;
import com.growup.ecountry.service.InvestService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/invest")
@RequiredArgsConstructor
public class InvestController {
    private final InvestService investService;

    @PostMapping("/set")
    public ResponseEntity<List<Invests>> setInvest(@RequestBody List<InvestDTO> investDTOs) {
        for(InvestDTO invest : investDTOs) {
            investService.create(invest);
        }
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping("/{countryId}")
    public ResponseEntity<List<InvestDTO>> getInvest(@PathVariable Long countryId) {
        return ResponseEntity.ok(investService.getInvest(countryId));
    }

    @PostMapping
    public ResponseEntity<Void> postInvest(@RequestBody InvestDTO investDTO) {
        investService.create(investDTO);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteInvest(@PathVariable Long id) {
        investService.deleteInvest(id);
        return ResponseEntity.ok().build();
    }
}
