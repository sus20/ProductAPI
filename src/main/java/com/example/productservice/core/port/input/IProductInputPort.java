package com.example.productservice.core.port.input;

import com.example.productservice.core.model.Product;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface IProductInputPort {
    Product saveProduct(Product product, MultipartFile imageFile) throws IOException;
    List<Product> getAllProducts();
    Product getProductById(String id);
    Product updateProduct(String id, Product updatedProduct, MultipartFile imageFile) throws IOException;
    void deleteProduct(String id);
}
