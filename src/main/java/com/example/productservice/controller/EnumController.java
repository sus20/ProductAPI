package com.example.productservice.controller;


import com.example.productservice.model.Product.ProductStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("enums")
public class EnumController {

    @GetMapping("/product-status")
    public ProductStatus[] getProductStatus(){
        return ProductStatus.values();
    }
}
