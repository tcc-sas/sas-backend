package com.fatec.sasbackend.stock;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


public interface StockService {

    Page<StockDTO> findAllStock(Pageable pageable);
    Page<StockDTO> findPagedStockByFilter(Pageable pageable, String productId, String name);

    StockDTO findStockById(Long id);
    StockDTO updateStock(StockDTO dto);

    StockDTO registerStock(StockDTO dto);

}
