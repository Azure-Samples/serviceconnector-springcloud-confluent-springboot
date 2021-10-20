package com.example.demo;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import azure.confluent.examples.DataRecordAvro;
import lombok.extern.log4j.Log4j2;

@Log4j2
@Component
public class KafkaConsumer {

  @KafkaListener(topics = "#{'${io.confluent.developer.config.topic.name}'}")
  public void consume(final ConsumerRecord<Long, DataRecordAvro> consumerRecord) {
    log.info("Receive message: {} {}", consumerRecord.key(), consumerRecord.value());

  }
}
