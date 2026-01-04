package io.wikilive.eventconsumer.repository;

import io.wikilive.eventconsumer.model.WikimediaEvent;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface WikimediaEventRepository extends ElasticsearchRepository<WikimediaEvent, String> {
}
