package com.fatec.sasbackend.covered;

import com.fatec.sasbackend.product.ProductDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

public interface CoveredService {

    Page<Covered> findAllCovered(Pageable pageable);

    Page<Covered> findPagedCoveredByFilter(Pageable pageable, String beneficiaryName, String beneficiaryCpf, String crasName, String deliveryDate);

}
