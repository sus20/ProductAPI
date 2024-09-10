package com.example.productservice.adapter.out.persistence.mapper;

import com.example.productservice.adapter.out.persistence.entity.CustomerEntity;
import com.example.productservice.adapter.out.persistence.entity.ProductEntity;
import com.example.productservice.core.model.Customer;
import com.example.productservice.core.model.Product;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface EntityMapper {

    EntityMapper INSTANCE = Mappers.getMapper(EntityMapper.class);

    Product mapToProduct (ProductEntity productEntity);
    ProductEntity mapToProductEntity(Product product);

    Customer mapToCustomer (CustomerEntity customerEntity);
    CustomerEntity mapToCustomerEntity(Customer customer);
}
