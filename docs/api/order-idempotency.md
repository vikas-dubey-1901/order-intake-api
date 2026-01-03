# Idempotency & Deduplication

## Why Idempotency Exists

- REST clients may retry requests
- Kafka consumers operate with at-least-once delivery
- Duplicate processing must be prevented

---

## REST Idempotency

- Enforced via `X-Request-Id`
- Duplicate submissions return HTTP 409
- Idempotency is applied at the service layer

---

## Kafka Idempotency

- Each event carries a unique `eventId`
- Processed events are recorded persistently
- Duplicate events are ignored safely

---

## Design Principle

Idempotency is enforced **centrally**, not at ingress boundaries.  
Both REST and Kafka flows share the same deduplication logic.
