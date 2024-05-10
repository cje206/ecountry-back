package com.growup.ecountry.service;

import com.growup.ecountry.dto.InvestDTO;
import com.growup.ecountry.dto.InvestStatusDTO;
import com.growup.ecountry.entity.InvestStatus;
import com.growup.ecountry.entity.Invests;
import com.growup.ecountry.repository.InvestRepository;
import com.growup.ecountry.repository.InvestStatusRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class InvestService {
    private final InvestRepository investRepository;
    private final InvestStatusRepository investStatusRepository;

//    투자 항목
    public List<Invests> createInvest(List<InvestDTO> investDTOS) {
        List<Invests> invests = new ArrayList<>();
        for (InvestDTO investDTO: investDTOS) {
            Invests invest = Invests.builder()
                    .name(investDTO.getName())
                    .unit(investDTO.getUnit())
                    .info(investDTO.getInfo())
                    .countryId(investDTO.getCountryId())
                    .build();
            invests.add(invest);
        }
        return investRepository.saveAll(invests);
    }

    public List<InvestDTO> getInvest(Long countryId) {
        return investRepository.findByCountryId(countryId).stream().map(invest -> InvestDTO.builder()
                .id(invest.getId()).name(invest.getName()).unit(invest.getUnit()).info(invest.getInfo()).countryId(invest.getCountryId())
                .build()).collect(Collectors.toList());
    }

    public void deleteInvest(Long id) {
        investRepository.deleteById(id);
    }

    public Invests updateInfo(InvestDTO investDTO) {
        Invests invest = investRepository.findById(investDTO.getId()).orElseThrow(() -> new IllegalArgumentException("존재하지 않는 ID 입니다."));
        invest.setInfo(investDTO.getInfo());
        return investRepository.save(invest);
    }

//    투자 현황
    public List<InvestStatusDTO> getStatus(Long investId) {
        return investStatusRepository.findByInvestId(investId).stream().map(status -> InvestStatusDTO.builder()
                .id(status.getId()).status(status.getStatus()).createdAt(status.getCreatedAt()).investId(status.getInvestId()).build())
                .collect(Collectors.toList());
    }

    public InvestStatus addStatus(InvestStatusDTO investStatusDTO) {
        InvestStatus status = InvestStatus.builder()
                .status(investStatusDTO.getStatus())
                .investId(investStatusDTO.getInvestId()).build();
        return investStatusRepository.save(status);
    }

    public void deleteStatus(Long id) {
        investStatusRepository.deleteById(id);
    }

}
