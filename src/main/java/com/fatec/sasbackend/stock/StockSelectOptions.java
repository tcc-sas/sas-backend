package com.fatec.sasbackend.stock;

import com.fatec.sasbackend.cras.CrasDTO;
import com.fatec.sasbackend.product.ProductDTO;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Builder
@Data
public class StockSelectOptions {
    List<ProductDTO> product;
    List<CrasDTO> cras;
}
