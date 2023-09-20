package br.com.communicationbetweensystems.grpc.domain;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Entity(name = "Product")
@Table(name = "products")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String name;

    @NotNull
    private double price;

    @NotNull
    private Integer quantityInStock;

    @Deprecated
    public Product() {}
    public Product(Long id, String name, double price, Integer quantityInStock) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.quantityInStock = quantityInStock;
    }


    public Long getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public double getPrice() {
        return this.price;
    }

    public Integer getQuantityInStock() {
        return this.quantityInStock;
    }
}
