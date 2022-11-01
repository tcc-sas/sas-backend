package com.fatec.sasbackend.stock;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;


public interface StockService {

    Page<StockDTO> findAllStock(Pageable pageable);
    Page<StockDTO> findPagedStockByFilter(Pageable pageable, String product, String cras);
    List<Stock> saveInStock(List<StockDTO> dto);
    StockRegistrationOptionsDTO findRegistrationOptions();
    StockSelectOptions findSelectOptions();
}
