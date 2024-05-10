package com.growup.ecountry.service;

import com.growup.ecountry.dto.InvestDTO;
import com.growup.ecountry.dto.InvestInfoDTO;
import com.growup.ecountry.entity.InvestInfoes;
import com.growup.ecountry.entity.Invests;
import com.growup.ecountry.repository.InvestInfoRepository;
import com.growup.ecountry.repository.InvestRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class InvestService {
    private final InvestRepository investRepository;
    private final InvestInfoRepository investInfoRepository;

//    투자 항목
    public List<Invests> create(List<InvestDTO> investDTOS) {
        List<Invests> invests = new ArrayList<>();
        for (InvestDTO investDTO: investDTOS) {
            Invests invest = Invests.builder()
                    .name(investDTO.getName())
                    .unit(investDTO.getUnit())
                    .countryId(investDTO.getCountryId())
                    .build();
            invests.add(invest);
        }
        return investRepository.saveAll(invests);
    }

    public List<InvestDTO> getInvest(Long countryId) {
        return investRepository.findByCountryId(countryId).stream().map(invest -> InvestDTO.builder()
                .id(invest.getId()).name(invest.getName()).unit(invest.getUnit()).countryId(invest.getCountryId())
                .build()).collect(Collectors.toList());
    }

    public void deleteInvest(Long id) {
        investRepository.deleteById(id);
    }

//    투자 정보
    public List<InvestInfoes> createInfo(List<InvestInfoDTO> investInfoDTOS) {
        List<InvestInfoes> infoes = investInfoDTOS.stream().map(investInfoDTO -> InvestInfoes.builder()
                .name(investInfoDTO.getName())
                .unit(investInfoDTO.getUnit())
                .countryId(investInfoDTO.getCountryId()).build()).toList();
        return investInfoRepository.saveAll(infoes);
    }

//    투자 현황

}
