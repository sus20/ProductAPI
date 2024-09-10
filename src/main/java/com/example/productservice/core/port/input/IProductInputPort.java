package com.example.productservice.core.port.input;

import com.example.productservice.core.model.Product;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface IProductInputPort {
    Product saveProduct(String customerId, Product product, MultipartFile imageFile) throws IOException;
    List<Product> getAllProducts(String customerId);
    Product getProductById(String customerId, String productId);
    Product updateProduct(String customerId, String productId, Product updatedProduct, MultipartFile imageFile) throws IOException;
    void deleteProduct(String customerId, String productId);
}
