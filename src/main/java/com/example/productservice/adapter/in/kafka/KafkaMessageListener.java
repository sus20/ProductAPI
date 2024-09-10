package com.example.productservice.adapter.in.kafka;

import io.cloudevents.CloudEvent;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;


@Slf4j
@Component
@AllArgsConstructor
public class KafkaMessageListener {

    private final KafkaConsumerHandler kafkaConsumerHandler;

    @KafkaListener(topics = "${topic.customerCreated}")
    public void listenCustomerCreated(CloudEvent event) {
        processEvent(event, kafkaConsumerHandler::handleCustomerCreatedEvent);
    }

    @KafkaListener(topics = "${topic.customerUpdated}")
    public void listenCustomerUpdated(CloudEvent event) {
        processEvent(event, kafkaConsumerHandler::handleCustomerUpdatedEvent);
    }

    @KafkaListener(topics = "${topic.customerDeleted}")
    public void listenCustomerDeleted(CloudEvent event) {
        processEvent(event, kafkaConsumerHandler::handleCustomerDeletedEvent);
    }

    private void processEvent(CloudEvent event, EventConsumer consumer) {
        try {
            consumer.accept(event);
        } catch (Exception e) {
            log.error("Failed to process event: {}", event.getId(), e);
        }
    }

    @FunctionalInterface
    private interface EventConsumer {
        void accept(CloudEvent event);
    }
}