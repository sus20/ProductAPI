package com.example.productservice.adapter.out.persistence.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.TypeAlias;

import java.math.BigDecimal;
import java.util.UUID;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "products")
@TypeAlias("com.example.productservice.adapter.out.persistence.entity.ProductEntity")
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class ProductEntity {

    @Id()
    private String id;

    private String name;
    private String description;
    private BigDecimal price;
    private Integer quantityInStock;

    @Lob
    private byte[] image;

    @Enumerated(EnumType.STRING)
    private com.example.productservice.core.model.Product.ProductStatus status;

    public enum ProductStatus {
        AVAILABLE,
        OUT_OF_STOCK,
        DISCONTINUED
    }

    public void generateID(){
        this.id = String.valueOf(UUID.randomUUID());
    }
}
