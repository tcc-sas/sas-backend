package com.fatec.sasbackend.stock;

import com.fatec.sasbackend.cras.Cras;
import com.fatec.sasbackend.product.Product;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StockDTO {

    private Long id;
    private Product product;
    private Integer productQuantity;
    private Cras cras;
}
