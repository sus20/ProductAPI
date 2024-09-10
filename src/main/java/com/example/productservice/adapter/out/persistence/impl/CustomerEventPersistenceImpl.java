package com.example.productservice.adapter.out.persistence.impl;

import com.example.productservice.adapter.out.persistence.CustomerRepository;
import com.example.productservice.adapter.out.persistence.entity.CustomerEntity;
import com.example.productservice.adapter.out.persistence.mapper.EntityMapper;
import com.example.productservice.core.model.Customer;
import com.example.productservice.core.port.output.ICustomerEventOutputPort;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
@AllArgsConstructor
public class CustomerEventPersistenceImpl implements ICustomerEventOutputPort {

    private final CustomerRepository customerRepository;

    @Override
    public Customer save(Customer customer) {
        CustomerEntity customerEntity = getCustomerEntity(customer);
        CustomerEntity savedCustomerEntity = customerRepository.save(customerEntity);
        return getCustomer(savedCustomerEntity);
    }

    @Override
    public Optional<Customer> findById(String id) {
        Optional<CustomerEntity> customerEntity = customerRepository.findById(id);
        return customerEntity.map(this::getCustomer).or(Optional::empty);
    }

    @Override
    public void deleteById(String id) {
        customerRepository.deleteById(id);
    }

    private Customer getCustomer(CustomerEntity customerEntity) {
        return EntityMapper.INSTANCE.mapToCustomer(customerEntity);
    }

    private CustomerEntity getCustomerEntity(Customer customer) {
        return EntityMapper.INSTANCE.mapToCustomerEntity(customer);
    }
}
