package io.wikilive.eventinterceptor.service;

public interface WikimediaKafkaProducer {

  void produce(String topic, String msg);
}
