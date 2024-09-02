package com.example.productservice.adapter.out.persistence.impl;

import com.example.productservice.adapter.out.persistence.ProductRepository;
import com.example.productservice.adapter.out.persistence.entity.ProductEntity;
import com.example.productservice.adapter.out.persistence.mapper.EntityMapper;
import com.example.productservice.core.model.Product;
import com.example.productservice.core.port.output.IProductOutputPersistencePort;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class ProductPersistenceImpl implements IProductOutputPersistencePort {

    private final ProductRepository productRepository;

    @Override
    public Product save(Product product) {
        ProductEntity productEntity = productRepository.save(getProductEntity(product));
        return getProduct(productEntity);
    }

    @Override
    public List<Product> findAll() {
        return productRepository.findAll().stream().map(this::getProduct).toList();
    }

    @Override
    public Optional<Product> findById(String id) {
        Optional<ProductEntity> productEntity = productRepository.findById(id);
        return productEntity.map(this::getProduct).or(Optional::empty);
    }

    @Override
    public void deleteById(String id) {
        productRepository.deleteById(id);

    }

    private  Product getProduct(ProductEntity productEntity){
        return EntityMapper.INSTANCE.mapToProduct(productEntity);
    }

    private ProductEntity getProductEntity(Product product){
        return EntityMapper.INSTANCE.mapToProductEntity(product);
    }

}
