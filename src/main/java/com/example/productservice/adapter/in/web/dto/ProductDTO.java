package com.example.productservice.adapter.in.web.dto;


import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class ProductDTO {

    private String id;

    @NotBlank(message = "{NotBlank.name}")
    private String name;

    @NotBlank(message = "{NotBlank.description}")
    private String description;

    @NotNull(message = "{NotNull.price}")
    @DecimalMin(value = "0.0", inclusive = false, message = "{DecimalMin.price}")
    private BigDecimal price;

    @NotNull(message = "{NotNull.quantityInStock}")
    @Min(value = 0, message = "{Min.quantityInStock}")
    private Integer quantityInStock;

    private byte[] image;

    @NotNull(message = "{NotNull.status}")
    private ProductStatus status;

    private String customerId;

    public enum ProductStatus {
        AVAILABLE,
        OUT_OF_STOCK,
        DISCONTINUED
    }
}
