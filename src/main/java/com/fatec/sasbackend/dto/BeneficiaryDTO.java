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

    private Long beneficiaryId;
    private String name;
    private String rg;
    private String cpf;

    private String zipCode;
    private String adress;
    private Integer houseNumber;
    private String district;
    private String city;

    private String phoneNumber;
    private LocalDate birthDate;
    private CrasDTO cras;
}
