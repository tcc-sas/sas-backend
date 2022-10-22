package com.fatec.sasbackend.beneficiary;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BeneficiaryProductsRepository extends JpaRepository<BeneficiaryProducts, Long> {

    @Modifying
    @Query(
            " DELETE FROM BeneficiaryProducts bp WHERE bp.beneficiary.id = :beneficiaryId"
    )
    void deleteAllByBeneficiaryId(@Param("beneficiaryId") Long beneficiaryId);

//    @Query(
//            " SELECT bp FROM BeneficiaryProducts bp WHERE bp.beneficiary.id = :beneficiaryId"
//    )
    List<BeneficiaryProducts> findAllByBeneficiaryId(Long beneficiaryId);

}

