package com.growup.ecountry.service;

import com.growup.ecountry.dto.AccountListDTO;
import com.growup.ecountry.entity.AccountLists;
import com.growup.ecountry.repository.AccountListRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AccountService {
    private final AccountListRepository accountListRepository;

    public AccountLists createList(AccountListDTO accountListDTO) {
        return accountListRepository.save(AccountLists.builder().name(accountListDTO.getName())
                .interest(accountListDTO.getInterest()).dueDate(accountListDTO.getDueDate())
                .division(true).available(true)
                .countryId(accountListDTO.getCountryId()).build());
    }

    public List<AccountListDTO> getList(Long countryId, boolean division, boolean available) {
        return accountListRepository.findByCountryIdAndDivisionAndAvailable(countryId, division, available).stream().map(list -> AccountListDTO.builder()
                .id(list.getId()).division(division).name(list.getName()).interest(list.getInterest())
                .dueDate(list.getDueDate()).available(available).countryId(list.getCountryId())
                .build()).collect(Collectors.toList());
    }

    public AccountLists updateList(AccountListDTO accountListDTO) {
        AccountLists list = accountListRepository.findById(accountListDTO.getId()).orElseThrow(() -> new IllegalArgumentException("존재하지 않는 ID 입니다."));
        list.setName(accountListDTO.getName());
        list.setInterest(accountListDTO.getInterest());
        list.setDueDate(accountListDTO.getDueDate());
        return accountListRepository.save(list);
    }

    public AccountLists disableList(Long id) {
        AccountLists list = accountListRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("존재하지 않는 ID 입니다."));
        list.setAvailable(false);
        return accountListRepository.save(list);
    }
}
