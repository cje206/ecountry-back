package com.growup.ecountry.service;

import com.growup.ecountry.dto.InvestDTO;
import com.growup.ecountry.entity.Invests;
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

    public Invests create(InvestDTO investDTO) {
        Invests invest = Invests.builder()
                .name(investDTO.getName())
                .unit(investDTO.getUnit())
                .countryId(investDTO.getCountryId())
                .build();
        return investRepository.save(invest);
    }

    public List<InvestDTO> getInvest(Long countryId) {
        return investRepository.findByCountryId(countryId).stream().map(invest -> InvestDTO.builder()
                .id(invest.getId()).name(invest.getName()).unit(invest.getUnit()).countryId(invest.getCountryId())
                .build()).collect(Collectors.toList());
    }

    public void deleteInvest(Long id) {
        investRepository.deleteById(id);
    }

}
