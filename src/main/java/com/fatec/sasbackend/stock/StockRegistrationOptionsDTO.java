package com.fatec.sasbackend.stock;

import com.fatec.sasbackend.cras.CrasDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class StockRegistrationOptionsDTO {

    List<StockProductsRegistrationDTO> products;
    List<CrasDTO> cras;
}
