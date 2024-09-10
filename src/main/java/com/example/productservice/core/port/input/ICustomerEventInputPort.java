package com.example.productservice.core.port.input;

import com.example.productservice.core.model.Customer;

public interface  ICustomerEventInputPort {
    void handleCustomerCreatedEvent(Customer customer);
    void handleCustomerUpdatedEvent(Customer customer);
    void handleCustomerDeletedEvent(Customer customer);
}
