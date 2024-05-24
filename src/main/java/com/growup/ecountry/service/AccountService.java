package com.growup.ecountry.service;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.growup.ecountry.dto.AccountDTO;
import com.growup.ecountry.dto.AccountListDTO;
import com.growup.ecountry.dto.BankDTO;
import com.growup.ecountry.entity.AccountLists;
import com.growup.ecountry.entity.Accounts;
import com.growup.ecountry.entity.Banks;
import com.growup.ecountry.entity.Countries;
import com.growup.ecountry.repository.AccountListRepository;
import com.growup.ecountry.repository.AccountRepository;
import com.growup.ecountry.repository.BankRepository;
import com.growup.ecountry.repository.CountryRepository;
import lombok.*;
import org.apache.poi.ss.formula.functions.Days;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AccountService {
    private final AccountListRepository accountListRepository;
    private final AccountRepository accountRepository;
    private final BankRepository bankRepository;
    private final CountryRepository countryRepository;

    public AccountLists createList(AccountListDTO accountListDTO) {
        return accountListRepository.save(AccountLists.builder().name(accountListDTO.getName())
                .interest(accountListDTO.getInterest()).dueDate(accountListDTO.getDueDate())
                .division(true).available(true)
                .countryId(accountListDTO.getCountryId()).build());
    }

    public List<AccountListDTO> getList(Long countryId, boolean division, boolean available) {
        return accountListRepository.findByCountryIdAndDivisionAndAvailable(countryId, division, available).stream().map(list -> AccountListDTO.builder()
                .id(list.getId()).division(list.getDivision()).name(list.getName()).interest(list.getInterest())
                .dueDate(list.getDueDate()).available(list.getAvailable()).countryId(list.getCountryId())
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

    //전체 통장조회
    public List<AccountInfo> getAccount(Long studentId) {
        System.out.println(accountRepository.findByStudentId(studentId));
        List<AccountInfo> accountInfoList =  new ArrayList<>();
        try {
            List<AccountDTO> accountDTOList = accountRepository.findByStudentId(studentId).stream().map(account -> AccountDTO.builder().id(account.getId())
                    .balance(account.getBalance()).createdAt(account.getCreatedAt())
                    .studentId(account.getStudentId()).accountListId(account.getAccountListId()).build()).toList();
            for(AccountDTO accountDTO :accountDTOList){
                AccountInfo accountInfo = new AccountInfo(accountDTO.getId(),accountDTO.getBalance(),accountDTO.getCreatedAt());
                AccountLists accountListDTO = accountListRepository.findById(accountDTO.getAccountListId()).orElseThrow();
                accountInfo.setting(accountListDTO.getName(),accountListDTO.getDivision(),accountListDTO.getInterest(),accountListDTO.getDueDate());
                accountInfoList.add(accountInfo);
            }
         } catch (Exception e){
            System.out.println("전체 통장리스트 오류 : " + e.getMessage());
        }
        return accountInfoList;
    }

    //통장상세정보 조회
    public AccountInfo getAccountDetail(Long id) {
        try {
            Accounts account = accountRepository.findById(id).orElseThrow();
            AccountLists accountList = accountListRepository.findById(account.getAccountListId()).orElseThrow();
            AccountInfo accountInfo =new AccountInfo(account.getId(),account.getBalance(),account.getCreatedAt());
            accountInfo.setting(accountList.getName(),accountList.getDivision(), accountList.getInterest(),accountList.getDueDate());
            return accountInfo;

        } catch (Exception e){
            System.out.println("통장 정보 상세조회 오류 : " + e.getMessage());
            return null;
        }
    }

    //적금가입
    public Boolean createSaving(AccountDTO accountDTO){

        try {
            Accounts accounts = Accounts.builder()
                .accountListId(accountDTO.getAccountListId())
                .studentId(accountDTO.getStudentId()).balance(0)
                .build();
            accountRepository.save(accounts);
        }catch (Exception e){
            System.out.println("적금가입오류 : " + e.getMessage());
            return false;
        }
        return true;
    }

    //학생별 가입된 적금리스트 조회
    public List<SavingList> findSavingList(Long id){
        List<SavingList> savingLists = new ArrayList<>();
        List<Accounts> accountsList = accountRepository.findByStudentId(id);

        for(Accounts accounts :accountsList){
            SavingList savingList = new SavingList(accounts.getId(),accounts.getBalance(),accounts.getCreatedAt());
            AccountLists savingAccountInfo = accountListRepository.findById(accounts.getAccountListId()).orElseThrow();
            if(savingAccountInfo.getDueDate() != null) {
                savingList.setInterestAndDueDate(savingAccountInfo.getInterest(), savingAccountInfo.getDueDate());
                savingList.setName(savingAccountInfo.getName());
                savingLists.add(savingList);
            }
        }
        return savingLists ;
    }

    //적금해지
    public Boolean closeSaving(Long countryId, Long studentId, AccountDTO accountDTO){
        try{
            //적금통장없애기
            accountRepository.deleteById(accountDTO.getId());
            //적금 금액 넣어주기
            //입출금통장 번호 조회
            List<AccountLists> accountLists = accountListRepository.findByCountryIdAndDivisionAndAvailable(countryId, false, true);
            accountDTO.setAccountListId(accountLists.get(0).getId());
            // 입출금통장 조회
            Accounts studentAccount = accountRepository.findByAccountListId(accountDTO.getAccountListId()).get(0);

            Banks bank = Banks.builder().transaction(accountDTO.getBalance()).memo("적금해지").isPenalty(0L).depositId(studentAccount.getId()).withdrawId(null)
                    .build();
            //bank에 거래내역 업데이트
            bankRepository.save(bank);
            // 학생 account에 금액 업데이트
            studentAccount.setBalance(studentAccount.getBalance() + accountDTO.getBalance());
            accountRepository.save(studentAccount);
            //국고에 금액 업데이트
            Countries country = countryRepository.findById(countryId).orElseThrow();
            country.setTreasury(country.getTreasury()-accountDTO.getBalance());
            countryRepository.save(country);


        }catch (Exception e){
            System.out.println("적금해지 에러 : " + e.getMessage());
            return false;
        }
        return true;
    }

    @Getter
    @Setter
    @Builder
    @AllArgsConstructor
    public static class SavingList{
        private Long id;
        private int balance;
        private double interest;
        private Timestamp createdAt;
        private int dueDate;
        private Date expirationDate;
        private String name;

        public SavingList(Long id,int balance,  Timestamp createdAt) {
            this.balance = balance;
            this.id = id;
            this.createdAt = createdAt;
        }
        public void setInterestAndDueDate(double interest, int dueDate){
            this.interest = interest;
            this.dueDate = dueDate;
            calExpirationDate();
        }
        public void calExpirationDate(){
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(createdAt);
            calendar.add(Calendar.DATE, dueDate);
            expirationDate = calendar.getTime();
            System.out.println("만기일" + expirationDate);

            Date now = new Date();
            Calendar today = Calendar.getInstance();
            today.setTime(now);

            if(calendar.compareTo(today) <= 0){
                //적금만기일 당일 또는 이후
                balance = (int) Math.ceil(balance * (1 + interest/100));

            }
            System.out.println("만기금액 : " + balance);

        }
    }

    public static class AccountInfo{
        @JsonProperty
        private Long id;
        @JsonProperty
        private Integer balance;
        @JsonProperty
        private Timestamp createdAt;
        @JsonProperty
        private String accountName;
        @JsonProperty
        private String  division;
        @JsonProperty
        private Double interest;
        @JsonProperty
        private Integer dueDate;

        public AccountInfo(Long id, Integer balance, Timestamp createdAt){
            this.id = id;
            this.balance = balance;
            this.createdAt = createdAt;
        }
        public void setting(String accountName, Boolean type, Double interest, Integer dueDate){
            this.division = type ?  "적금통장" :  "입출금통장";
            this.dueDate = dueDate;
            this.accountName = accountName;
            this.interest = interest;
        }
    }

}
