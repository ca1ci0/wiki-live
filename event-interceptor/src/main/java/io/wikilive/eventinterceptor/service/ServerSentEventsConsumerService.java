package io.wikilive.eventinterceptor.service;

import org.springframework.http.codec.ServerSentEvent;
import reactor.core.publisher.Flux;

public interface ServerSentEventsConsumerService {

  Flux<ServerSentEvent<String>> consume(String uri);
}
