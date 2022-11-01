package com.fatec.sasbackend.covered;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.time.LocalDate;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class CoveredServiceImpl implements CoveredService {

    private final CoveredRepository repository;

    @Override
    public Page<Covered> findAllCovered(Pageable pageable) {
        return repository.findAll(pageable);
    }

    @Override
    public Page<Covered> findPagedCoveredByFilter(Pageable pageable, String beneficiaryName, String beneficiaryCpf, String crasName, String deliveryDate) {

        LocalDate deliveryDate2 = null;
        if(StringUtils.hasText(deliveryDate)){
           deliveryDate2 = LocalDate.parse(deliveryDate);
        }

        return repository.findPagedCoveredByFilter(beneficiaryName, beneficiaryCpf, crasName, deliveryDate2, pageable);
    }
}
