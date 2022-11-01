package com.fatec.sasbackend.covered;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;

@Repository
public interface CoveredRepository extends JpaRepository <Covered, Long> {

    @Query(
            " SELECT c " +
                    " FROM Covered c" +
                    " WHERE " +
                    " (:beneficiaryName = ''     OR LOWER(c.beneficiaryName) LIKE CONCAT('%', :beneficiaryName, '%')) " +
                    " AND (:beneficiaryCpf = ''  OR LOWER(c.beneficiaryCpf) = :beneficiaryCpf )" +
                    " AND (:crasName = ''        OR LOWER(c.crasName) = :crasName )" +
                    " AND (:deliveryDate is null OR :deliveryDate = c.benefitDeliveryDate)"
    )
    Page<Covered> findPagedCoveredByFilter(
            @Param("beneficiaryName") String beneficiaryName,
            @Param("beneficiaryCpf") String beneficiaryCpf,
            @Param("crasName") String crasName,
            @Param("deliveryDate") LocalDate deliveryDate,
            Pageable pageable);
}
