package com.growup.ecountry.controller;

import com.growup.ecountry.config.TokenProvider;
import com.growup.ecountry.dto.AccountDTO;
import com.growup.ecountry.dto.AccountListDTO;
import com.growup.ecountry.dto.ApiResponseDTO;
import com.growup.ecountry.dto.TokenDTO;
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
    private final TokenProvider jwt;

    @PostMapping
    public ResponseEntity<ApiResponseDTO<NullType>> createList(@RequestBody AccountListDTO accountListDTO) {
        try {
            accountService.createList(accountListDTO);
            return ResponseEntity.ok(new ApiResponseDTO<>(true, "적금 통장 추가 완료"));
        } catch (Exception e) {
            return ResponseEntity.ok(new ApiResponseDTO<>(false, "적금 통장 추가 실패"));

        }
    }

    @GetMapping("/{countryId}")
    public ResponseEntity<ApiResponseDTO<List<AccountListDTO>>> getList(@PathVariable Long countryId) {
        try {
            List<AccountListDTO> result = accountService.getList(countryId, true, true);
            return ResponseEntity.ok(new ApiResponseDTO<>(true, "개설 가능 적금 통장 조회 완료", result));
        } catch (Exception e) {
            return ResponseEntity.ok(new ApiResponseDTO<>(false, "개설 가능 적금 통장 조회 실패"));
        }

    }

    @PatchMapping
    public ResponseEntity<ApiResponseDTO<NullType>> updateList(@RequestBody AccountListDTO accountListDTO) {
        try {
            accountService.updateList(accountListDTO);
            return ResponseEntity.ok(new ApiResponseDTO<>(true, "적금 통장 수정 완료"));
        } catch (Exception e) {
            return ResponseEntity.ok(new ApiResponseDTO<>(false, "적금 통장 수정 실패"));
        }
    }

    @PatchMapping("/delete/{id}")
    public ResponseEntity<ApiResponseDTO<NullType>> disableList(@PathVariable Long id) {
        try {
            accountService.disableList(id);
            return ResponseEntity.ok(new ApiResponseDTO<>(true, "적금 통장 비활성화 완료"));
        } catch (Exception e) {
            return ResponseEntity.ok(new ApiResponseDTO<>(false, "적금 통장 비활성화 실패"));
        }
    }
    //적금가입
    @PostMapping("/saving")
    public ResponseEntity<ApiResponseDTO<NullType>> createSaving(@RequestHeader(value = "Authorization") String token,@RequestBody AccountDTO accountDTO){
        TokenDTO authToken = jwt.validateToken(token);
        accountDTO.setStudentId(authToken.getId());
        boolean success = accountService.createSaving(accountDTO);
        String msg = success ? "적금통장 개설에 성공하였습니다." : "적금통장 개설에 실패하였습니다. ";
        return ResponseEntity.ok(new ApiResponseDTO<NullType>(success, msg));
    }

    //학생별 가입된 적금리스트 조회
    @GetMapping("/saving")
    public ResponseEntity<ApiResponseDTO<List<AccountService.SavingList>>> findAllSaving(@RequestHeader(value = "Authorization") String token){
        TokenDTO authToken = jwt.validateToken(token);
        List<AccountService.SavingList> savingLists  = accountService.findSavingList(authToken.getId());
        return ResponseEntity.ok(new ApiResponseDTO<List<AccountService.SavingList>>(true, "가입된 적금리스트 정보 조회에 성공하였습니다.", savingLists));
    }

    //적금해지
    @DeleteMapping("/saving/{countryId}")
    public ResponseEntity<ApiResponseDTO<NullType>> closeSaving(@RequestHeader(value = "Authorization") String token, @RequestBody AccountDTO accountDTO, @PathVariable Long countryId){
        TokenDTO authToken = jwt.validateToken(token);
        boolean success = accountService.closeSaving(countryId, authToken.getId(), accountDTO);
        String msg = success ? "적금통장 해지에 성공하였습니다." : "적금통장 해지에 실패하였습니다. ";
        return ResponseEntity.ok(new ApiResponseDTO<NullType>(success, msg));
    }


}
