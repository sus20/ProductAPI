package com.example.productservice.core.service;

import com.example.productservice.core.model.Customer;
import com.example.productservice.core.port.input.ICustomerEventInputPort;
import com.example.productservice.core.port.output.ICustomerEventOutputPort;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;


@Slf4j
@Service
@AllArgsConstructor
public class CustomerEventService implements ICustomerEventInputPort {

    private final ICustomerEventOutputPort iCustomerEventOutputPort;

    @Override
    public void handleCustomerCreatedEvent(Customer customer) {
        log.info("Processing customer created event: {}", customer);
        iCustomerEventOutputPort.save(customer);
    }

    @Override
    public void handleCustomerUpdatedEvent(Customer customer) {
        log.info("Processing customer updated event: {}", customer);
        updateCustomerEvent(customer.getId(), customer);
    }

    @Override
    public void handleCustomerDeletedEvent(Customer customer) {
        log.info("Processing customer deleted event: {}", customer);
        iCustomerEventOutputPort.deleteById(customer.getId());
    }

    private void updateCustomerEvent(String id, Customer updatedCustomer) {
        Customer customer = findCustomerById(id);
        updatedCustomer.setId(customer.getId());
        iCustomerEventOutputPort.save(updatedCustomer);
    }

    private Customer findCustomerById(String id)  {
        return iCustomerEventOutputPort.findById(id).orElseThrow(() -> new RuntimeException("Customer not found Exception"));
    }
}

