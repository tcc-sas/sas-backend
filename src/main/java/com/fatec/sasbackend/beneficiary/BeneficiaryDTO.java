package com.fatec.sasbackend.beneficiary;

import com.fatec.sasbackend.converter.LocalDateConverter;
import com.fatec.sasbackend.cras.CrasDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Convert;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class BeneficiaryDTO {

    private Long id;

    @NotBlank(message = "Name cannot be blank")
    private String name;
    private String rg;

    @NotBlank(message = "CPF cannot be blank")
    private String cpf;

    private String zipCode;
    private String adress;
    private Integer houseNumber;
    private String district;
    private String city;

    private String phoneNumber;

    @NotNull(message = "Birthdate cannot be null")
    @Convert(converter = LocalDateConverter.class)
    private LocalDate birthDate;

    @NotNull(message = "At least one CRAS must be selected")
    private CrasDTO cras;
}
