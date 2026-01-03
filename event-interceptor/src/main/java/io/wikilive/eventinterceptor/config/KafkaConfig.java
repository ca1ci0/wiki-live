package io.wikilive.eventinterceptor.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class KafkaConfig {

  @Bean
  public NewTopic wikimediaTopic(@Value("${wikimedia.kafka.topic}") String wikimediaTopicName) {
    return TopicBuilder.name(wikimediaTopicName)
        .partitions(3)
        .replicas(1)
        .build();
  }
}
