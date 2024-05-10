package com.growup.ecountry.controller;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.growup.ecountry.dto.ApiResponseDTO;
import com.growup.ecountry.dto.RuleDTO;
import com.growup.ecountry.service.RuleService;
import lombok.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/rule")
@RequiredArgsConstructor
public class RuleController {
    public final RuleService ruleService;

    //규칙리스트 등록
    @PostMapping
    public ResponseEntity<RuleResponse> createRules(@RequestBody List<RuleDTO> ruleDTOList){
        Boolean result = ruleService.setRules(ruleDTOList);
        String msg = result ? "규칙생성에 성공하였습니다." : "규칙생성에 실패하였습니다.";

        return ResponseEntity.ok(new RuleResponse(result, msg));
    }
    //규칙리스트 조회
    @GetMapping
    public ResponseEntity<FindAllRuleResponse> findAllRules(@RequestParam Long countryId){
        List<RuleDTO> ruleDTOList = ruleService.findAllRules(countryId);
        String msg = ruleDTOList != null ? "규칙조회에 성공하였습니다." : "규칙조회에 실패하였습니다.";

        List<FindedAllRules> findedAllRulesList = new ArrayList<>() ;

        for(RuleDTO ruleDTO:ruleDTOList){
            findedAllRulesList.add(new FindedAllRules(ruleDTO.getId(), ruleDTO.getRule()));
        }

       FindAllRuleResponse hello = new FindAllRuleResponse(true, msg,  findedAllRulesList);

        return ResponseEntity.ok(hello);
    }

    //규칙 삭제
    @DeleteMapping
    public ResponseEntity<RuleResponse> deleteRule(@RequestParam long id){
        Boolean result = ruleService.deleteRules(id);
        String msg = result ? "규칙삭제에 성공하였습니다." : "규칙삭제에 실패하였습니다.";

        return ResponseEntity.ok(new RuleResponse(result, msg));
    }
    //규칙 업데이트
    @PatchMapping
    public ResponseEntity<RuleResponse> updateRule(@RequestBody RuleDTO ruleDTO){
        Boolean result = ruleService.updateRule(ruleDTO);
        String msg = result ? "규칙수정에 성공하였습니다." : "규칙수정에 실패하였습니다.";

        return ResponseEntity.ok(new RuleResponse(result, msg));
    }

    //규칙세팅,추가,업데이트, 삭제 응답
    static class RuleResponse extends ApiResponseDTO {

        public RuleResponse(Boolean success, String message) {
            super(success, message, null);
        }
    }
    //규칙리스트 정보 응답
    static class FindAllRuleResponse  extends ApiResponseDTO<FindedAllRules>{

        @Builder
        public FindAllRuleResponse(Boolean success, String message, List<FindedAllRules> findedAllRulesList) {
            super(success, message,findedAllRulesList);
        }
    }
    //규칙리스트
    static class FindedAllRules{
        @JsonProperty
        private Long id;
        @JsonProperty
        private String rule;

        public FindedAllRules(Long id, String rule){
            this.id = id;
            this.rule = rule;
        }
    }
}
