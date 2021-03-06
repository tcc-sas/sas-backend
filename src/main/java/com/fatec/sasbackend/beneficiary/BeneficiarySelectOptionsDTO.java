package com.fatec.sasbackend.beneficiary;

import com.fatec.sasbackend.cras.CrasDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BeneficiarySelectOptionsDTO {
    private List<CrasDTO> cras;
}
