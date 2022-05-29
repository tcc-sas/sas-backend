package com.fatec.sasbackend.stock;

import com.fatec.sasbackend.cras.Cras;
import com.fatec.sasbackend.product.Product;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "stock")
public class Stock {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "product_id", referencedColumnName = "id")
    private Product productId;

    @Column(name = "product_quantity")
    private Long productQuantity;

    @ManyToOne
    @JoinColumn(name = "cras_id", referencedColumnName = "id")
    private Cras crasId;

}
