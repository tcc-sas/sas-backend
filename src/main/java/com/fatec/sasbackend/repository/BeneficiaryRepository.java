package com.fatec.sasbackend.repository;

import com.fatec.sasbackend.entity.Beneficiary;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BeneficiaryRepository extends JpaRepository<Beneficiary, Long> {

    Boolean existsByBeneficiaryId(Long beneficiaryId);

    @Query(
            "SELECT b FROM Beneficiary b "+
                    "LEFT JOIN b.cras c "+
                    "WHERE "+
                    "(:name = '' OR lower(b.name) = lower(:name)) "+
                    "AND (:cpf = '' OR b.cpf = :cpf) "+
                    "AND (:cras = '' OR c.id = :cras) "
    )
    List<Beneficiary> findPagedBeneficiaryByFilter(@Param("name") String name,
                                                   @Param("cpf") String cpf,
                                                   @Param("cras") String cras,
                                                   Pageable pageable);


}
