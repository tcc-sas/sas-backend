package com.fatec.sasbackend.product;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


public interface ProductService {

    Page<ProductDTO> findAllProducts(Pageable pageable);
    Page<ProductDTO> findPagedProductsByFilter(Pageable pageable, String productId, String name);

    ProductDTO findProductById(Long id);
    ProductDTO updateProduct(ProductDTO dto);

    ProductDTO registerProduct(ProductDTO dto);
}
