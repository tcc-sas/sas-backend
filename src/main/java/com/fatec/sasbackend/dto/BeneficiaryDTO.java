package com.fatec.sasbackend.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class BeneficiaryDTO {

    private Long id;
    private String name;
    private String beneficiaryId;
    private CrasDTO cras;
    private String zipCode;
    private String adress;
    private String phoneNumber;
    private LocalDate birthDate;
}
