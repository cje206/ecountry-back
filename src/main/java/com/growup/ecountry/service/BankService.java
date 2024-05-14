package com.growup.ecountry.service;

import com.growup.ecountry.dto.AccountDTO;
import com.growup.ecountry.dto.ApiResponseDTO;
import com.growup.ecountry.dto.BankDTO;
import com.growup.ecountry.entity.Accounts;
import com.growup.ecountry.entity.Banks;
import com.growup.ecountry.entity.Jobs;
import com.growup.ecountry.entity.Students;
import com.growup.ecountry.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BankService {
    private final AccountRepository accountRepository;
    private final BankRepository bankRepository;
    private final StudentRepository studentRepository;
    private final AccountListRepository accountListRepository;
    private final JobRepository jobRepository;

    public String getStudentName(Long accountId) {
        Long studentId = accountRepository.findById(accountId).orElseThrow(() -> new IllegalArgumentException("존재하지 않는 ID 입니다.")).getStudentId();
        return studentRepository.findById(studentId).orElseThrow(() -> new IllegalArgumentException("존재하지 않는 ID 입니다.")).getName();
    }

    public BankDTO createBank(BankDTO bankDTO) {
        System.out.println(getStudentName(bankDTO.getDepositId()));

        Banks result = bankRepository.save(Banks.builder().transaction(bankDTO.getTransaction())
                .memo(bankDTO.getMemo()).depositId(bankDTO.getDepositId())
                .withdrawId(bankDTO.getWithdrawId()).build());
        Accounts deposit = accountRepository.findById(bankDTO.getDepositId()).orElseThrow(() -> new IllegalArgumentException("존재하지 않는 ID 입니다."));
        deposit.setBalance(deposit.getBalance()+bankDTO.getTransaction());
        accountRepository.save(deposit);
        Accounts withdraw = accountRepository.findById(bankDTO.getWithdrawId()).orElseThrow(() -> new IllegalArgumentException("존재하지 않는 ID 입니다."));
        withdraw.setBalance(withdraw.getBalance()-bankDTO.getTransaction());
        accountRepository.save(withdraw);
        return BankDTO.builder().id(result.getId()).transaction(result.getTransaction())
                .createdAt(result.getCreatedAt()).memo(result.getMemo()).depositId(result.getDepositId())
                .withdrawId(result.getWithdrawId()).depositName(getStudentName(result.getDepositId())).build();
    }

    public List<BankDTO> getBank(Long accountId) {
        return bankRepository.findByDepositIdOrWithdrawId(accountId, accountId).stream().map(list -> BankDTO.builder()
                .id(list.getId()).transaction(list.getTransaction()).createdAt(list.getCreatedAt())
                .memo(list.getMemo()).isPenalty(list.getIsPenalty()).depositId(list.getDepositId()).withdrawId(list.getWithdrawId())
                .depositName(getStudentName(list.getDepositId())).withdrawName(getStudentName(list.getWithdrawId())).build()).collect(Collectors.toList());
    }

    public List<AccountDTO> getBankList(Long countryId) {
        Long accountListId = accountListRepository.findByCountryIdAndDivisionAndAvailable(countryId, false, true).get(0).getId();
        return accountRepository.findByAccountListId(accountListId).stream().map(account -> AccountDTO.builder()
                .id(account.getId()).name(getStudentName(account.getId())).build()).collect(Collectors.toList());
    }
    //월급명세서
    //월급금액확인
    public ApiResponseDTO<Banks> checkSalary(Long countryId, Long studentId) {
        Students student = studentRepository.findByIdANDCountryId(studentId, countryId).orElseThrow(() -> new IllegalArgumentException("존재하지 않는 회원입니다"));
        Jobs jobs = jobRepository.findById(student.getJobId()).orElseThrow(() -> new IllegalArgumentException("존재하지 않는 직업입니다"));
        List<Accounts> accounts = accountRepository.findByStudentId(student.getId());
        // Bank.id 로 조회하기
        Banks banks = Banks.builder()
                .transaction(jobs.getSalary())
                .memo("월급")
                .depositId(accounts.get(0).getId())
                .withdrawId(0L)
                .build();
        bankRepository.save(banks);
        return new ApiResponseDTO<>(true, "월급금액 확인", banks);
    }
}
