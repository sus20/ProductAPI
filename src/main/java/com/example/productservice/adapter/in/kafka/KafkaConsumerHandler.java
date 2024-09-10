package com.example.productservice.adapter.in.kafka;

import com.example.productservice.adapter.in.kafka.serde.ObjectDeserializer;
import com.example.productservice.core.model.Customer;
import com.example.productservice.core.port.input.ICustomerEventInputPort;
import io.cloudevents.CloudEvent;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Slf4j
@Service
@AllArgsConstructor
public class KafkaConsumerHandler {

    private final ICustomerEventInputPort customerEventInputPort;

    public void handleCustomerCreatedEvent(CloudEvent event) {
        Customer customer = deserializeCustomerEvent(event);
        customerEventInputPort.handleCustomerCreatedEvent(customer);
    }

    public void handleCustomerUpdatedEvent(CloudEvent event) {
        Customer customer = deserializeCustomerEvent(event);
        customerEventInputPort.handleCustomerUpdatedEvent(customer);
    }

    public void handleCustomerDeletedEvent(CloudEvent event) {
        Customer customer = deserializeCustomerEvent(event);
        customerEventInputPort.handleCustomerDeletedEvent(customer);
    }

    private Customer deserializeCustomerEvent(CloudEvent event) {
        try {
            byte [] eventData = Objects.requireNonNull(event.getData()).toBytes();
            return ObjectDeserializer.fromByte(eventData, Customer.class);
        } catch (Exception e) {
            log.error("Failed to deserialize event: {}", event.getId(), e);
            throw new RuntimeException("Failed to deserialize Customer event", e);
        }
    }
}