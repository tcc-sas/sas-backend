package com.fatec.sasbackend.covered;

import com.fatec.sasbackend.util.converter.LocalDateConverter;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "vw_covered")
public class Covered {

    @Id
    private Long id;

    @Column(name = "beneficiary_id")
    private Long beneficiaryId;

    @Column(name = "beneficiary_name")
    private String beneficiaryName;

    @Column(name = "beneficiary_cpf")
    private String beneficiaryCpf;

    @Column(name = "cras_name")
    private String crasName;

    @Convert(converter = LocalDateConverter.class)
    @Column(name = "benefit_delivery_date")
    private LocalDate benefitDeliveryDate;
}
