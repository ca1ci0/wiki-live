package io.wikilive.eventinterceptor;

import io.wikilive.eventinterceptor.service.ServerSentEventsConsumerService;
import io.wikilive.eventinterceptor.service.WikimediaKafkaProducer;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import org.springframework.http.codec.ServerSentEvent;
import reactor.core.publisher.Flux;

@Slf4j
@SpringBootApplication
public class WikimediaEventInterceptorApplication {

  public static void main(String[] args) {
    SpringApplication.run(WikimediaEventInterceptorApplication.class, args);
  }

  @Profile("!test")
  @Bean
  CommandLineRunner start(
      ServerSentEventsConsumerService service,
      WikimediaKafkaProducer kafkaProducer,
      @Value("${wikimedia.sse.endpoint}") String wikimediaSseUri,
      @Value("${wikimedia.kafka.topic}") String wikimediaTopic
  ) {
    return args -> {
      Flux<ServerSentEvent<String>> eventStream = service.consume(wikimediaSseUri);

      eventStream.subscribe(event ->
          Optional.ofNullable(event)
              .map(ServerSentEvent::data)
              .ifPresentOrElse(
                  data -> {
                    log.info("Wikimedia event msg: {}", data);
                    kafkaProducer.produce(wikimediaTopic, data);
                  },
                  () -> log.info("Skipped null-data event")));
    };
  }
}
