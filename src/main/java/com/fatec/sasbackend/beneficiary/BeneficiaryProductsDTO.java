package com.fatec.sasbackend.beneficiary;

import com.fatec.sasbackend.product.SimpleProductDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class BeneficiaryProductsDTO {

    private Long id;
    private Long beneficiaryId;
    private List<SimpleProductDTO> productsDTO;

}
