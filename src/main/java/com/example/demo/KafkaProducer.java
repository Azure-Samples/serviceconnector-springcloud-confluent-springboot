package com.example.demo;

import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import azure.confluent.examples.DataRecordAvro;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

import static java.util.stream.IntStream.range;

@Log4j2
@Component
@RequiredArgsConstructor
public class KafkaProducer {

    private final KafkaTemplate<String, DataRecordAvro> producer;
    private final NewTopic topic;

    //  @EventListener(ApplicationStartedEvent.class)
    public String produce() {
        // Produce sample data
        range(0, 10).forEach(i -> {
            final String key = "testKey";
            final DataRecordAvro record = new DataRecordAvro((long) i);
            log.info("Produce message: {}\t{}", key, record);
            producer.send(topic.name(), key, record).addCallback(
                    result -> {
                        final RecordMetadata m;
                        if (result != null) {
                            m = result.getRecordMetadata();
                            log.info("Produced record to topic {} partition {} @ offset {}",
                                    m.topic(),
                                    m.partition(),
                                    m.offset());
                        }
                    },
                    exception -> log.error("Failed to produce to kafka", exception));
        });

        producer.flush();

        log.info("10 messages were produced to topic {}", topic.name());
        return "10 messages were produced to topic " + topic.name();
    }

}
