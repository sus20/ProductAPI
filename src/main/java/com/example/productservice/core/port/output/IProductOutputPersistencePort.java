package com.example.productservice.core.port.output;

import com.example.productservice.core.model.Product;

import java.util.List;
import java.util.Optional;

public interface IProductOutputPersistencePort {

    Product save(Product product);
    List<Product> findAll();
    Optional<Product> findById(String id);
    void deleteById(String id);
}
