package com.example.productservice.service;

import com.example.productservice.model.Product;
import com.example.productservice.repository.ProductRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

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

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    public Product getProductById(UUID id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Product with ID " + id + " does not exist."));
    }

    public Product updateProduct(UUID id, Product updatedProduct, MultipartFile imageFile) throws IOException {
        Product product = productRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Product with ID " + id + "does not exits."));
        updatedProduct.setId(product.getId());
        if (imageFile != null && !imageFile.isEmpty()) {
            updatedProduct.setImage(imageFile.getBytes());
        }
        return productRepository.save(updatedProduct);
    }

    public void deleteProduct(UUID id) {
        productRepository.deleteById(id);
    }
}