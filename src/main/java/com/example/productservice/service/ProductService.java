package com.example.productservice.service;


import com.example.productservice.model.Product;
import com.example.productservice.repository.ProductRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
@AllArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

    public Product saveProduct(Product product, MultipartFile imageFile) throws IOException {
        if (imageFile != null && !imageFile.isEmpty()) {
            product.setImage(imageFile.getBytes());
        }
        return productRepository.save(product);
    }
}
