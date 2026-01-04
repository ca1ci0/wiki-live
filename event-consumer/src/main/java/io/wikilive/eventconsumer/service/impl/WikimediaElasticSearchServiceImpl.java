package io.wikilive.eventconsumer.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.wikilive.eventconsumer.model.WikimediaEvent;
import io.wikilive.eventconsumer.repository.WikimediaEventRepository;
import io.wikilive.eventconsumer.service.WikimediaElasticSearchService;
import java.util.List;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class WikimediaElasticSearchServiceImpl implements WikimediaElasticSearchService {

  private final WikimediaEventRepository repository;
  private final ObjectMapper objectMapper;

  @Override
  public void saveBatch(List<String> batch) {
    log.info("Saving batch of {} events", batch.size());

    List<WikimediaEvent> events = batch.stream()
        .map(this::parseEvent)
        .filter(Objects::nonNull)
        .toList();

    repository.saveAll(events);
    log.info("Successfully saved {} events", events.size());
  }

  private WikimediaEvent parseEvent(String jsonEvent) {
    try {
      return objectMapper.readValue(jsonEvent, WikimediaEvent.class);
    } catch (Exception e) {
      log.error("Failed to parse event: {}", e.getMessage());
      return null;
    }
  }
}
