package com.fatec.sasbackend.stock;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class StockProductsRegistrationDTO {

    private Long id;
    private String name;
    private Integer quantity;
}
