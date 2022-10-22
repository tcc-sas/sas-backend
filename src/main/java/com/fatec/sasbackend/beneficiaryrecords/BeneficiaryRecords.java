package com.fatec.sasbackend.beneficiaryrecords;

import com.fatec.sasbackend.beneficiary.Beneficiary;
import com.fatec.sasbackend.converter.LocalDateConverter;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
@Table(name = "beneficiary_records")
public class BeneficiaryRecords {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @ManyToOne
    @JoinColumn(name = "beneficiary_id", referencedColumnName = "id")
    private Beneficiary beneficiary;


    @Convert(converter = LocalDateConverter.class)
    @Column(name = "benefit_delivery_date")
    private LocalDate benefitDeliveryDate;




}
