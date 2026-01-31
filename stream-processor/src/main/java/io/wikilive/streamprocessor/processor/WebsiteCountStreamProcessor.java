package io.wikilive.streamprocessor.processor;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.time.Duration;
import java.util.Map;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.Materialized;
import org.apache.kafka.streams.kstream.Produced;
import org.apache.kafka.streams.kstream.TimeWindows;
import org.apache.kafka.streams.kstream.WindowedSerdes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class WebsiteCountStreamProcessor {

  public static final String WEBSITE_COUNT_STORE = "website-count-store";
  private static final String WEBSITE_COUNT_TOPIC = "wikimedia.stats.website";
  private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

  @Autowired
  public void setup(StreamsBuilder streamsBuilder) {
    KStream<String, String> inputStream = streamsBuilder.stream("wikimedia.recentchange");
    final TimeWindows timeWindows = TimeWindows.ofSizeWithNoGrace(Duration.ofMinutes(1L));
    inputStream
        .selectKey((k, changeJson) -> {
          try {
            final JsonNode jsonNode = OBJECT_MAPPER.readTree(changeJson);
            return jsonNode.get("server_name")
                .asText();
          } catch (IOException e) {
            return "parse-error";
          }
        })
        .groupByKey()
        .windowedBy(timeWindows)
        .count(Materialized.as(WEBSITE_COUNT_STORE))
        .toStream()
        .mapValues((key, value) -> {
          final Map<String, Object> kvMap = Map.of(
              "website", key.key(),
              "count", value
          );
          try {
            return OBJECT_MAPPER.writeValueAsString(kvMap);
          } catch (JsonProcessingException e) {
            return null;
          }
        })
        .to(WEBSITE_COUNT_TOPIC, Produced.with(
            WindowedSerdes.timeWindowedSerdeFrom(String.class, timeWindows.size()),
            Serdes.String()
        ));
  }
}
