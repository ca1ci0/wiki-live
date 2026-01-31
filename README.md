# Wiki Live

Real-time Wikimedia event streaming and analytics platform.

## Architecture

```
Wikimedia SSE → event-interceptor → Kafka → event-consumer → Elasticsearch
                                         ↓
                                  stream-processor (Kafka Streams)
```

## Modules

### event-interceptor
Consumes Wikimedia Server-Sent Events and publishes to Kafka.
- Port: 8081

### event-consumer
Consumes events from Kafka and indexes to Elasticsearch.
- Port: 8082

### stream-processor
Real-time stream processing with Kafka Streams for statistics.
- Port: 8083
- **API Documentation:** http://localhost:8083/swagger-ui.html

## API Endpoints

### Stream Processor Statistics
- `GET /stats/user-type` - Event counts by user type (bot/human)
- `GET /stats/website` - Event counts by website

## Infrastructure

Start services with Docker Compose:
```bash
docker-compose up -d
```

Services:
- Kafka: localhost:9092
- Kafka UI: http://localhost:8080
- Elasticsearch: http://localhost:9200
- Kibana: http://localhost:5601
- Grafana: http://localhost:3000 (admin/admin)

## Running

```bash
# Start all modules
./gradlew bootRun

# Or individually
./gradlew :event-interceptor:bootRun
./gradlew :event-consumer:bootRun
./gradlew :stream-processor:bootRun
```
