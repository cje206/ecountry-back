package com.growup.ecountry.service;

import com.growup.ecountry.dto.RuleDTO;
import com.growup.ecountry.entity.Countries;
import com.growup.ecountry.entity.Rules;
import com.growup.ecountry.repository.RuleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RuleService {
    private final RuleRepository ruleRepository;

    //규칙리스트 등록
    public Boolean setRules(List<RuleDTO> ruleDTOList){
        try {
            List<Rules> rulesList = ruleDTOList.stream().map(ruleDTO -> Rules.builder()
                    .rule(ruleDTO.getRule())
                    .countryId(ruleDTO.getCountryId())
                    .build()).collect(Collectors.toList());

            ruleRepository.saveAll(rulesList);
        } catch (Exception e){
            System.out.println(e.getMessage());
            return false;
        }
        return true;
    }

    //규칙리스트 정보
    public List<RuleDTO> findAllRules(Long countryId){
        List<Rules> rules = ruleRepository.findByCountryId(countryId);
        return rules.stream().map(rule -> RuleDTO.builder()
                        .id(rule.getId())
                        .rule(rule.getRule())
                        .build()).collect(Collectors.toList());
    }

    //규칙 삭제
    public Boolean deleteRules(Long id){
        try {
            ruleRepository.deleteById(id);
            //deleteById 값 변환이 없어서 id가 없어도 오류나지 않음
        }catch (Exception e){
            System.out.println(e.getMessage());
            return false;
        }
        return true;
    }
    // 규칙 수정
    public Boolean updateRule (RuleDTO ruleDTO){

        try {
            Rules rule = ruleRepository.findById(ruleDTO.getId()).orElseThrow(() -> new IllegalArgumentException("존재하지 않는 규칙아이디 입니다."));
            rule.setRule(ruleDTO.getRule());
            ruleRepository.save(rule);
        }catch (Exception e){
            System.out.println(e.getMessage());
            return false;
        }
        return true;
    }

}
