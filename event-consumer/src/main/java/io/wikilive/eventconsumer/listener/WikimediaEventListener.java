package io.wikilive.eventconsumer.listener;

import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class WikimediaEventListener {

  @KafkaListener(topics = "${wikimedia.kafka.topic}")
  public void processWikimediaRecentChangeBatch(List<String> messageBatch) {
    log.info("Received wikimedia message batch: {}", messageBatch);
  }
}
