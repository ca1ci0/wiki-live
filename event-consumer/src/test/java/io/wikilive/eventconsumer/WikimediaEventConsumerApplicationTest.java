package io.wikilive.eventconsumer;

import io.wikilive.eventconsumer.repository.WikimediaEventRepository;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

@SpringBootTest
class WikimediaEventConsumerApplicationTest {

  @MockitoBean
  private WikimediaEventRepository repository;

  @Test
  void contextLoads() {
  }
}