package com.example.productservice.core.port.output;


import com.example.productservice.core.model.Customer;

import java.util.Optional;

public interface ICustomerEventOutputPort {
    Customer save(Customer customer);
    Optional<Customer> findById(String id);
    void deleteById(String id);
}