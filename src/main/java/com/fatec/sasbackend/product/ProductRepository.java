package com.fatec.sasbackend.product;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {


    @Query(
            " SELECT p " +
                    " FROM Product p WHERE " +
                    " (:productId = '' OR p.productId = :productId) " +
                    " AND (:name = '' OR LOWER(p.name) LIKE CONCAT('%', :name, '%')) "
    )
    Page<Product> findPagedProductsByFilter(
            @Param("productId") String productId,
            @Param("name") String name,
            Pageable pageable);


    @Query(
          "SELECT " +
                  "CASE " +
                      "WHEN COUNT(p) > 0 THEN TRUE " +
                      "ELSE FALSE " +
                  "END "+
                  " FROM Product p " +
                  " WHERE (p.name = :name AND p.id <> :id)"
    )
    Boolean checkIfNameIsAlreadyTakenToUpdate(
            @Param("id") Long id,
            @Param("name") String name);


    @Query(
            " SELECT " +
                    " CASE " +
                        " WHEN COUNT(p) > 0 THEN TRUE " +
                        " ELSE FALSE " +
                    " END " +
                    " FROM Product p " +
                    " WHERE p.name = :name "

    )
    Boolean checkIfNameIsAlreadyTaken(
            @Param("name") String name);



    @Query(
            " SELECT p FROM Product p" +
                    " WHERE p.availableQuantity > 0"
    )
    List<Product> findAllProductsWithAvailableQuantity();

}
