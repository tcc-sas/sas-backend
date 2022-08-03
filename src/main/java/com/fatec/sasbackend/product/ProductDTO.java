package com.fatec.sasbackend.product;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductDTO {

    private Long id;

    private String productId;

    private String name;

    private String unity;

    private String description;

    private Integer availableQuantity;
}
