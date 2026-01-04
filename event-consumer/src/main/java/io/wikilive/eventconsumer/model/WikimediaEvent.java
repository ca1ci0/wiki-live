package io.wikilive.eventconsumer.model;

import static lombok.AccessLevel.PRIVATE;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.Instant;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

@Data
@NoArgsConstructor
@FieldDefaults(level = PRIVATE)
@Document(indexName = "#{@environment.getProperty('wikimedia.index')}")
public class WikimediaEvent {

  @Id
  String id;

  @Field(type = FieldType.Object)
  Meta meta;

  @Field(type = FieldType.Keyword)
  String type;

  @Field(type = FieldType.Integer)
  Integer namespace;

  @Field(type = FieldType.Text)
  String title;

  @JsonProperty("title_url")
  @Field(type = FieldType.Keyword)
  String titleUrl;

  @Field(type = FieldType.Text)
  String comment;

  @Field(type = FieldType.Long)
  Long timestamp;

  @Field(type = FieldType.Keyword)
  String user;

  @Field(type = FieldType.Boolean)
  Boolean bot;

  @Field(type = FieldType.Boolean)
  Boolean minor;

  @Field(type = FieldType.Object)
  Length length;

  @Field(type = FieldType.Object)
  Revision revision;

  @JsonProperty("server_name")
  @Field(type = FieldType.Keyword)
  String serverName;

  @Field(type = FieldType.Keyword)
  String wiki;

  @Field(type = FieldType.Date)
  Instant indexedAt = Instant.now();

  @Data
  @NoArgsConstructor
  @FieldDefaults(level = PRIVATE)
  public static class Meta {

    String uri;
    @JsonProperty("request_id")
    String requestId;
    String id;
    String domain;
    String stream;
    String dt;
    String topic;
    Integer partition;
    Long offset;
  }

  @Data
  @NoArgsConstructor
  @FieldDefaults(level = PRIVATE)
  public static class Length {

    @JsonProperty("old")
    Integer oldLength;
    @JsonProperty("new")
    Integer newLength;
  }

  @Data
  @NoArgsConstructor
  @FieldDefaults(level = PRIVATE)
  public static class Revision {

    @JsonProperty("old")
    Long oldRevision;
    @JsonProperty("new")
    Long newRevision;
  }
}
