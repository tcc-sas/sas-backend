package com.fatec.sasbackend.stock;

import com.fatec.sasbackend.cras.Cras;
import com.fatec.sasbackend.product.Product;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.core.annotation.AliasFor;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "stock")
public class Stock {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "product_id", referencedColumnName = "id")
    private Product product;

    @Column(name = "product_quantity")
    private Integer productQuantity;

    @ManyToOne
    @JoinColumn(name = "cras_id", referencedColumnName = "id")
    private Cras cras;

}
