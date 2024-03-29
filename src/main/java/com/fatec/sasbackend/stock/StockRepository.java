package com.fatec.sasbackend.stock;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StockRepository extends JpaRepository<Stock, Long> {


    @Query(
            " SELECT s " +
                    " FROM Stock s " +
                    " JOIN s.product p "+
                    " JOIN s.cras c "+
                "WHERE " +
                    " (:product = '' OR p.id = :product) " +
                    " AND (:cras = '' OR c.id = :cras)"
    )
    Page<Stock> findPagedStockByFilter(
            @Param("product") String product,
            @Param("cras") String cras,
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



    List<Stock> findAllByCrasIdAndProductIdIn(Long crasId, Iterable<Long> productsId);
}
