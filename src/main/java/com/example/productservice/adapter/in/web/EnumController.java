package com.example.productservice.adapter.in.web;

import com.example.productservice.core.exception.InvalidParameterException;
import com.example.productservice.core.model.Product.ProductStatus;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/products")
public class EnumController {

    public final String PRODUCT_STATUS = "product-status";

    @GetMapping("/datalist")
    public ProductStatus[] getProductStatus(@RequestParam String name){
        if(StringUtils.pathEquals(PRODUCT_STATUS, name)){
            return ProductStatus.values();
        }else{
            throw new InvalidParameterException(name);
        }
    }
}