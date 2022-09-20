package com.fatec.sasbackend.stock;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface StockRepository extends JpaRepository<Stock, Long> {


    @Query(
            " SELECT s " +
                    " FROM Stock s " +
                    "LEFT JOIN s.product p "+
                    "LEFT JOIN s.cras c "+
                "WHERE " +
                    " (:product is null OR p.id = :product) " +
                    " AND (:cras is null OR c.id = :cras)"
    )
    Page<Stock> findPagedStockByFilter(
            @Param("product") Long product,
            @Param("cras") Long cras,
            Pageable pageable);


    @Query(
          " SELECT s " +
              " FROM Stock s " +
              " LEFT JOIN s.product p "+
              " LEFT JOIN s.cras c "+
              " WHERE " +
                  " p.id = :product AND" +
                  " c.id = :cras "
    )
    Stock checkIfProductIsOnStockByNameAndCras(
            @Param("product") Long product,
            @Param("cras") Long cras);



}
