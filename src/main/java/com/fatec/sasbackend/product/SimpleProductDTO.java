package com.fatec.sasbackend.product;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SimpleProductDTO {

    private Long id;
    private String name;
    private Integer quantity;
}
