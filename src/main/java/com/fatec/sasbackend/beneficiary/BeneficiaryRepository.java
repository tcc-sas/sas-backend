package com.fatec.sasbackend.beneficiary;

import com.fatec.sasbackend.beneficiary.Beneficiary;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BeneficiaryRepository extends JpaRepository<Beneficiary, Long> {


    @Query(
            "SELECT b FROM Beneficiary b "
    )
    List<Beneficiary> testeList(Pageable pageable);

    @Query(
            "SELECT b FROM Beneficiary b "
    )
    Slice<Beneficiary> testeSlice(Pageable pageable);

    @Query(
            "SELECT b FROM Beneficiary b " +
                    "LEFT JOIN b.cras c " +
                    "WHERE " +
                    "(:name = '' OR lower(b.name) LIKE CONCAT('%', :name, '%')) " +
                    "AND (:rg = '' OR b.rg = :rg) " +
                    "AND (:cpf = '' OR b.cpf = :cpf) " +
                    "AND (:cras = '' OR c.id = :cras) "
    )
    Page<Beneficiary> findPagedBeneficiaryByFilter(@Param("name") String name,
                                                   @Param("rg") String rg,
                                                   @Param("cpf") String cpf,
                                                   @Param("cras") String cras,
                                                   Pageable pageable);


    Boolean existsByCpf(@Param("cpf") String cpf);

    @Query(
            "SELECT CASE WHEN COUNT(b) > 0 THEN TRUE " +
                    " ELSE FALSE " +
                    " END " +
                    " FROM Beneficiary b " +
                    " WHERE b.cpf = :cpf AND b.id <> :id "
    )
    Boolean checkIfCpfAlreadyUsed(@Param("id") Long id,
                                   @Param("cpf") String cpf
    );
}

