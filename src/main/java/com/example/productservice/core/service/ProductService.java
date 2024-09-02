package com.example.productservice.core.service;

import com.example.productservice.core.exception.ProductCreationException;
import com.example.productservice.core.exception.ProductNotFoundException;
import com.example.productservice.core.model.Product;
import com.example.productservice.core.port.input.IProductInputPort;
import com.example.productservice.core.port.output.IProductOutputPersistencePort;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
@AllArgsConstructor
public class ProductService implements IProductInputPort {

    private final IProductOutputPersistencePort iProductOutputPersistencePort;

    public Product saveProduct(Product product, MultipartFile imageFile) {
        validateImageFile(imageFile);
        product.setImage(convertImageFileToBytes(imageFile));
        product.generateID();
        return iProductOutputPersistencePort.save(product);
    }

    public List<Product> getAllProducts() {
        return iProductOutputPersistencePort.findAll();
    }

    public Product getProductById(String id) {
        return iProductOutputPersistencePort.findById(id)
                .orElseThrow(() -> new ProductNotFoundException(id));
    }

    public Product updateProduct(String id, Product updatedProduct, MultipartFile imageFile) throws IOException {
        Product product = iProductOutputPersistencePort.findById(id).orElseThrow(() -> new ProductNotFoundException(id));
        updatedProduct.setId(product.getId());
        validateImageFile(imageFile);
        return iProductOutputPersistencePort.save(updatedProduct);
    }

    public void deleteProduct(String id) {
        iProductOutputPersistencePort.deleteById(id);
    }

    private void validateImageFile(MultipartFile imageFile) {
        if (ObjectUtils.isEmpty(imageFile) || imageFile.isEmpty()) {
            throw new ProductCreationException("Image file is null or empty");
        }
    }

    private byte[] convertImageFileToBytes(MultipartFile imageFile) {
        try {
            return imageFile.getBytes();
        } catch (IOException e) {
            throw new ProductCreationException("Failed to process image file", e);
        }
    }
}