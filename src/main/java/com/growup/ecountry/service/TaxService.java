package com.growup.ecountry.service;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.growup.ecountry.dto.AccountDTO;
import com.growup.ecountry.dto.BankDTO;
import com.growup.ecountry.dto.TaxDTO;
import com.growup.ecountry.entity.*;
import com.growup.ecountry.repository.*;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TaxService {
    private final TaxRepository taxRepository;
    private final BankRepository bankRepository;
    private final AccountListRepository accountListRepository;
    private final AccountRepository accountRepository;
    private  final CountryRepository countryRepository;

    //세금리스트 등록
    public Boolean addTaxes(List<TaxDTO> taxDTOList){
        try{
            List<Taxes> taxesList = taxDTOList.stream().map(taxDTO -> Taxes.builder()
                    .name(taxDTO.getName())
                    .division(taxDTO.getDivision())
                    .tax(taxDTO.getTax())
                    .countryId(taxDTO.getCountryId())
                    .build()).collect(Collectors.toList())
                    ;
            taxRepository.saveAll(taxesList);
        }catch (Exception e){
            System.out.println("세금리스트 등록 오류 : " + e.getMessage());
            System.out.println(e.getMessage());
            return false;
        }
        return true;
    }

    //세금리스트 조회
    public List<TaxDTO> findAllTaxes(Long countryId){
        List<Taxes> taxesList = taxRepository.findByCountryId(countryId);
        return taxesList.stream().map(tax -> TaxDTO.builder()
                .id(tax.getId())
                .name(tax.getName())
                .division(tax.getDivision())
                .tax(tax.getTax())
                .build()).collect(Collectors.toList());
    }

    //세금리스트 1개 삭제
    public Boolean deleteTax(Long id){
        try{
            taxRepository.deleteById(id);
        }catch (Exception e){
            System.out.println("세금리스트 1개 삭제 오류 : " + e.getMessage());
                System.out.println(e.getMessage());
            return false;
        }
        return true;
    }

    //세금리스트 1개 수정
    public Boolean updateTax(TaxDTO taxDTO){
        try{
            Taxes tax = taxRepository.findById(taxDTO.getId()).orElseThrow();
            tax.setTax(taxDTO.getTax());
            tax.setDivision(taxDTO.getDivision());
            tax.setName(taxDTO.getName());
            taxRepository.save(tax);
        }catch (Exception e){
            System.out.println("세금리스트 1개 수정 오류 : " + e.getMessage());
            System.out.println(e.getMessage());
            return false;
        }
        return true;
    }
    //과태료 부과된 학생 조회
    public List<BankDTO> findPenalty(Long countryId){
        List<Banks> penaltyList = bankRepository.findByIsPenalty(countryId);
        List<BankDTO> panaltyDTOList = new ArrayList<>();
        try{
            panaltyDTOList = penaltyList.stream().map(penalty -> BankDTO.builder().id(penalty.getId()).memo(penalty.getMemo()).transaction(penalty.getTransaction()).createdAt(penalty.getCreatedAt()).build()).toList();
        }catch (Exception e){
            System.out.println("과태료 조회 오류 : " + e.getMessage());
        }
        return panaltyDTOList;
    }
    //과태료 리스트 조회
    public List<PenaltyList> findPenaltyList(Long countryId){
        try {
            List<Taxes> taxesList = taxRepository.findByCountryIdAndDivision(countryId, 3);
            return taxesList.stream().map(tax ->
                    PenaltyList.builder().taxName(tax.getName()).tax(tax.getTax()).build()
            ).toList();
        }catch (Exception e){
            System.out.println("과태료 리스트 조회 오류 : "+e.getMessage());
            return null;
        }
    }
    //과태료 부과
    public Boolean imposePenalty(Long countryId,BankDTO bankDTO){
        try{
            Banks bank = Banks.builder().isPenalty(countryId).transaction(bankDTO.getTransaction()).memo(bankDTO.getMemo()).withdrawId(bankDTO.getWithdrawId()).depositId(null).build();

            //입출금통장 번호 조회
            List<AccountLists> accountLists = accountListRepository.findByCountryIdAndDivisionAndAvailable(countryId, false, true);
            AccountDTO accountDTO = AccountDTO.builder().balance(bankDTO.getTransaction()).accountListId(accountLists.get(0).getId()).build();
            // 입출금통장 조회
            Accounts studentAccount = accountRepository.findByAccountListId(accountDTO.getAccountListId()).get(0);

            //bank에 과태료부과내역 추가
            bankRepository.save(bank);
            // 학생 계좌에 금액 업데이트
            studentAccount.setBalance(studentAccount.getBalance() - accountDTO.getBalance());
            accountRepository.save(studentAccount);
            //국고에 금액 업데이트
            Countries country = countryRepository.findById(countryId).orElseThrow();
            country.setTreasury(country.getTreasury() + accountDTO.getBalance());
            countryRepository.save(country);
        }catch (Exception e){
            System.out.println("과태료 부과 오류 : " + e.getMessage());
            return false;
        }
        return true;
    }

    //과태료 삭제
    public boolean deletePenalty(Long id){
        try{
            Banks penaltyInfo = bankRepository.findById(id).orElseThrow();

            //입출금통장 번호 조회
            List<AccountLists> accountLists = accountListRepository.findByCountryIdAndDivisionAndAvailable(penaltyInfo.getIsPenalty(), false, true);
            AccountDTO accountDTO = AccountDTO.builder().balance(penaltyInfo.getTransaction()).accountListId(accountLists.get(0).getId()).build();
            // 입출금통장 조회
            Accounts studentAccount = accountRepository.findByAccountListId(accountDTO.getAccountListId()).get(0);

            // 학생 계좌에 금액 업데이트
            studentAccount.setBalance(studentAccount.getBalance() + accountDTO.getBalance());
            accountRepository.save(studentAccount);
            //국고에 금액 업데이트
            Countries country = countryRepository.findById(penaltyInfo.getIsPenalty()).orElseThrow();
            country.setTreasury(country.getTreasury() - accountDTO.getBalance());
            countryRepository.save(country);

            bankRepository.deleteById(id);
        }catch (Exception e){
            System.out.println("과태료 삭제 오류 " + e.getMessage());
            return false;
        }
        return true;
    }

    @Builder
    public static class PenaltyList{
        @JsonProperty
        private String taxName ;
        @JsonProperty
        private Double tax;
    }

}
