package com.growup.ecountry.service;

import com.growup.ecountry.dto.TaxDTO;
import com.growup.ecountry.entity.Taxes;
import com.growup.ecountry.repository.TaxRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TaxService {
    private final TaxRepository taxRepository;

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
            System.out.println(e.getMessage());
            return false;
        }
        return true;
    }
}
