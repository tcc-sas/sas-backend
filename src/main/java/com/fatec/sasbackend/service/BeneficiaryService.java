package com.fatec.sasbackend.service;

import com.fatec.sasbackend.dto.BeneficiaryDTO;
import com.fatec.sasbackend.dto.BeneficiarySelectOptionsDTO;
import com.fatec.sasbackend.model.user.UserUpdateModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface BeneficiaryService {

    Boolean registerBeneficiary(BeneficiaryDTO beneficiaryDTO);
    Page<BeneficiaryDTO> findAllBeneficiary(Pageable pageable);
    BeneficiaryDTO findBeneficiaryById(Long id);
    BeneficiarySelectOptionsDTO findSelectOptions();
    Page<BeneficiaryDTO> findPagedBeneficiaryByFilter(String name, String beneficiaryId, String cras, Pageable pageable);
    Boolean updateBeneficiary(BeneficiaryDTO model);
}
