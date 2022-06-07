package com.fatec.sasbackend.stock;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;


public interface StockService {

    Page<StockDTO> findAllStock(Pageable pageable);
    Page<StockDTO> findPagedStockByFilter(Pageable pageable, String productId, String name);
    List<StockDTO> saveInStock(List<StockDTO> dto);

}
