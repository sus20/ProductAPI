package com.example.productservice.core.service;

import com.example.productservice.core.exception.CustomerIdNotFoundException;
import com.example.productservice.core.exception.ImageProcessingException;
import com.example.productservice.core.exception.InvalidImageFileException;
import com.example.productservice.core.exception.ProductNotFoundException;
import com.example.productservice.core.model.Product;
import com.example.productservice.core.port.input.IProductInputPort;
import com.example.productservice.core.port.output.ICustomerEventOutputPort;
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
    private final ICustomerEventOutputPort iCustomerEventOutputPort;

    @Override
    public Product saveProduct(String customerId, Product product, MultipartFile imageFile) {
        validateCustomerExist(customerId);
        validateImageFile(imageFile);

        product.setCustomerId(customerId);
        product.setImage(convertImageFileToBytes(imageFile));
        return iProductOutputPersistencePort.save(product);
    }

    public List<Product> getAllProducts(String customerId) {
        validateCustomerExist(customerId);
        return iProductOutputPersistencePort.findAll();
    }

    public Product getProductById(String customerId, String productId) {
        validateCustomerExist(customerId);
        return findProductById(productId);
    }

    public Product updateProduct(String customerId, String productId, Product updatedProduct, MultipartFile imageFile) {
        validateCustomerExist(customerId);
        validateImageFile(imageFile);

        Product product = findProductById(productId);
        updatedProduct.setId(product.getId());
        return iProductOutputPersistencePort.save(updatedProduct);
    }

    public void deleteProduct(String customerId, String productId) {
        validateCustomerExist(customerId);
        iProductOutputPersistencePort.deleteById(productId);
    }

    private void validateImageFile(MultipartFile imageFile) {
        if (ObjectUtils.isEmpty(imageFile) || imageFile.isEmpty()) {
            throw new InvalidImageFileException("Image file is null or empty");
        }
    }

    private byte[] convertImageFileToBytes(MultipartFile imageFile) {
        try {
            return imageFile.getBytes();
        } catch (IOException e) {
            throw new ImageProcessingException("Failed to process image file", e);
        }
    }

    private Product findProductById(String id) {
        return iProductOutputPersistencePort.findById(id)
                .orElseThrow(() -> new ProductNotFoundException(id));
    }

    private void validateCustomerExist(String customerId){
        iCustomerEventOutputPort.findById(customerId).orElseThrow(() -> new CustomerIdNotFoundException(customerId));
    }
}