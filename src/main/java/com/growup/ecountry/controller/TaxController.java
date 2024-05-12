package com.growup.ecountry.controller;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.growup.ecountry.dto.ApiResponseDTO;
import com.growup.ecountry.dto.RuleDTO;
import com.growup.ecountry.dto.TaxDTO;
import com.growup.ecountry.service.TaxService;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.lang.model.type.NullType;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/tax")
@RequiredArgsConstructor
public class TaxController {
    public  final TaxService taxService;

    //세금리스트 등록
    @PostMapping()
    public ResponseEntity<ApiResponseDTO<NullType>> createTaxes(@RequestBody List<TaxDTO> taxDTOList){
        Boolean success = taxService.addTaxes(taxDTOList);
        String msg = success ? "세금리스트 추가에 성공하였습니다." : "세금리스트 추가에 실패하였습니다.";

        return ResponseEntity.ok(new ApiResponseDTO<NullType>(success,msg));
    }
    //세금리스트 조회
    @GetMapping("/{countryId}")
    public ResponseEntity<ApiResponseDTO<List<FindAllTaxes>>> findAllTaxes(@PathVariable Long countryId){
        List<TaxDTO> taxDTOList = taxService.findAllTaxes(countryId);
        String msg = taxDTOList != null ? "세금리스트 조회에 성공하였습니다." : "세금리스트 조회에 실패하였습니다.";
        boolean success = false;
        System.out.println("length : " + taxDTOList.size());
        List<FindAllTaxes> findAllTaxesList = new ArrayList<>();
        try {
            taxDTOList.forEach(taxDTO -> findAllTaxesList.add(
                    new FindAllTaxes(taxDTO.getId(), taxDTO.getName(), taxDTO.getDivision(), taxDTO.getTax()))
            );
            success = true;
        }catch (Exception e){
            msg = "세금리스트 조회에 실패하였습니다.";
        }
        return ResponseEntity.ok(new ApiResponseDTO<List<FindAllTaxes>>(success, msg, findAllTaxesList));
    }

    //세금리스트 삭제
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponseDTO<NullType>> deleteTax(@PathVariable Long id){
        Boolean success = taxService.deleteTax(id);
        String msg = success ? "세금리스트 1개 삭제에 성공하였습니다." : "세금리스트 1개 삭제에 실패하였습니다.";

        return ResponseEntity.ok(new ApiResponseDTO<NullType>(success, msg));
    }
    //세금리스트 수정
    @PatchMapping
    public ResponseEntity<ApiResponseDTO<NullType>> updateTax(@RequestBody TaxDTO taxDTO){
        Boolean success = taxService.updateTax(taxDTO);
        String msg = success ? "세금리스트 1개 수정에 성공하였습니다." : "세금리스트 1개 수정에 실패하였습니다.";

        return ResponseEntity.ok(new ApiResponseDTO<NullType>(success, msg));
    }

    static class FindAllTaxes{
        @JsonProperty
        private Long id;
        @JsonProperty
        private String name;
        @JsonProperty
        private Integer division;
        @JsonProperty
        private Double tax;

        public FindAllTaxes(Long id, String name, Integer division, Double tax){
            this.id = id;
            this.name = name;
            this.division = division;
            this.tax = tax;
        }
    }
}
