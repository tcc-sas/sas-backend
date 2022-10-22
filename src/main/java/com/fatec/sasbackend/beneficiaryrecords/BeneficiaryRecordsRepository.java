package com.fatec.sasbackend.beneficiaryrecords;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface BeneficiaryRecordsRepository extends JpaRepository<BeneficiaryRecords, Long> {

    @Query(
            " SELECT b" +
                " FROM BeneficiaryRecords b" +
                    " WHERE " +
                " b.beneficiary.id = :benficiaryId AND "+
                " YEAR(b.benefitDeliveryDate) = :year AND " +
                " MONTH(b.benefitDeliveryDate) = :month"
    )
    BeneficiaryRecords getByYearAndMonth(
            @Param("benficiaryId") Long benficiaryId,
            @Param("year") Integer year,
            @Param("month") Integer month
    );
}
