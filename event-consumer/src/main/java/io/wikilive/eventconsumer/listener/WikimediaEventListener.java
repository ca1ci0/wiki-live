package io.wikilive.eventconsumer.listener;

import io.wikilive.eventconsumer.service.WikimediaElasticSearchService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class WikimediaEventListener {

  private final WikimediaElasticSearchService elasticSearchService;

  @KafkaListener(topics = "${wikimedia.kafka.topic}")
  public void processWikimediaRecentChangeBatch(List<String> messageBatch) {
    log.info("Received wikimedia message batch: {}", messageBatch);
    elasticSearchService.saveBatch(messageBatch);
  }
}
