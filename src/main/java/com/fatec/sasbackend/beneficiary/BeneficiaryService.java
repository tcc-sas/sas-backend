package com.fatec.sasbackend.beneficiary;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface BeneficiaryService {

    BeneficiaryDTO registerBeneficiary(BeneficiaryDTO beneficiaryDTO);
    Page<BeneficiaryDTO> findAllBeneficiary(Pageable pageable);
    BeneficiaryDTO findById(Long id);
    BeneficiarySelectOptionsDTO findSelectOptions();
    Page<BeneficiaryDTO> findPagedBeneficiaryByFilter(String name, String rg, String cpf, String cras, Pageable pageable);
    BeneficiaryDTO updateBeneficiary(BeneficiaryDTO model);

    void deleteBeneficiary(String id);
}
