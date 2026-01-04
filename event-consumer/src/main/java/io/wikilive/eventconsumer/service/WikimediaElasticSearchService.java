package io.wikilive.eventconsumer.service;

import java.util.List;

public interface WikimediaElasticSearchService {

  void saveBatch(List<String> batch);
}
