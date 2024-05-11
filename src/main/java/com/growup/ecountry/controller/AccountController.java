package com.growup.ecountry.controller;

import com.growup.ecountry.dto.AccountListDTO;
import com.growup.ecountry.dto.ApiResponseDTO;
import com.growup.ecountry.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.lang.model.type.NullType;
import java.util.List;

@RestController
@RequestMapping("/api/account")
@RequiredArgsConstructor
public class AccountController {
    private final AccountService accountService;

    @PostMapping
    public ResponseEntity<ApiResponseDTO<NullType>> createList(@RequestBody AccountListDTO accountListDTO) {
        accountService.createList(accountListDTO);
        return ResponseEntity.ok(new ApiResponseDTO<>(true, "적금 통장 추가 완료", null));
    }

    @GetMapping("/{countryId}")
    public ResponseEntity<ApiResponseDTO<List<AccountListDTO>>> getList(@PathVariable Long countryId) {
        return ResponseEntity.ok(new ApiResponseDTO<>(true, "개설 가능 적금 통장 조회 완료", accountService.getList(countryId, true, true)));
    }

    @PatchMapping
    public ResponseEntity<ApiResponseDTO<NullType>> updateList(@RequestBody AccountListDTO accountListDTO) {
        accountService.updateList(accountListDTO);
        return ResponseEntity.ok(new ApiResponseDTO<>(true, "적금 통장 수정 완료", null));
    }

    @PatchMapping("/delete/{id}")
    public ResponseEntity<ApiResponseDTO<NullType>> disableList(@PathVariable Long id) {
        accountService.disableList(id);
        return ResponseEntity.ok(new ApiResponseDTO<>(true, "적금 통장 비활성화 완료", null));
    }

}
