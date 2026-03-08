

###  See [docs/api/README.md](docs/api/README.md)



# Phase 2 — Event-Driven Order Processing (Kafka + Outbox Pattern)

## Overview

Phase 2 introduces **asynchronous, event-driven processing** to the Order Processing system.
Instead of performing all operations synchronously within a single service, the system now uses **Apache Kafka and the Outbox Pattern** to ensure reliable communication between services.

This phase transforms the system into a **microservice-style architecture** where order creation and order processing are decoupled and handled through **events**.

The system now guarantees:

* Reliable event publishing
* Event-driven communication between services
* Idempotent request handling
* Asynchronous order processing
* Eventual consistency between services

---

# System Architecture

```
Client (REST)
      │
      ▼
┌────────────────────┐
│   orderProcessor   │
│  (Core Service)    │
└─────────┬──────────┘
          │
          │ Save Order
          ▼
      PostgreSQL
          │
          │ Outbox Pattern
          ▼
   outbox_events table
          │
          │ Scheduled Publisher
          ▼
        Kafka
   Topic: order-received
          │
          ▼
┌────────────────────┐
│     orderClient    │
│  Processing Worker │
└─────────┬──────────┘
          │
          │ Process Order
          ▼
        Kafka
   Topic: order-processed
          │
          ▼
┌────────────────────┐
│   orderProcessor   │
│  Kafka Consumer    │
└─────────┬──────────┘
          │
          ▼
   Update Order Status
```

---

# Services

## 1. orderProcessor (Main Service)

This service is responsible for:

* Accepting **REST requests** to create orders
* Persisting order data
* Writing events to the **Outbox table**
* Publishing events to Kafka
* Consuming processing results from Kafka
* Updating order status

### Key Components

**Order API**

* Handles order creation requests.

**Idempotency Layer**

* Prevents duplicate order processing.

**Outbox Repository**

* Stores events in the database before publishing.

**Outbox Publisher**

* Scheduled job that publishes events to Kafka.

**Kafka Consumer**

* Listens for processed order events.

---

## 2. orderClient (Processing Service)

This service acts as an **asynchronous worker**.

Responsibilities:

* Consumes `ORDER_RECEIVED` events
* Processes orders
* Publishes `ORDER_PROCESSED` events back to Kafka

This service demonstrates **event-driven processing across microservices**.

---

# Database Tables

## orders

Stores the main order details.

Example fields:

```
order_id
customer_id
status
currency
created_at
updated_at
```

---

## order_items

Stores items associated with the order.

```
id
order_id
product_id
quantity
unit_price
```

---

## processed_requests

Ensures **idempotency** by tracking processed request IDs.

Flow:

```
Incoming Request
      │
Check request_id
      │
Already processed?
      │
Yes → Ignore
No → Process order
```

---

## outbox_events

Stores events before publishing them to Kafka.

```
id
aggregate_type
aggregate_id
event_type
payload
status
created_at
published_at
```

Status values:

```
PENDING
PUBLISHED
```

---

# Event Flow

## Step 1 — Order Creation

Client sends a request:

```
POST /orders
```

The system:

1. Saves the order
2. Stores an idempotency record
3. Creates an outbox event

---

## Step 2 — Outbox Publisher

A scheduled job runs every few seconds:

```
@Scheduled(fixedDelay = 5000)
```

It:

* Reads **PENDING events**
* Publishes them to Kafka
* Marks them as **PUBLISHED**

---

## Step 3 — Order Processing Service

The `orderClient` service consumes the event:

```
Topic: order-received
```

Event example:

```
{
  "orderId": "...",
  "customerId": "CUST-100",
  "status": "RECEIVED"
}
```

The service processes the order and publishes a new event.

---

## Step 4 — Processing Result Event

`orderClient` produces:

```
Topic: order-processed
```

Example payload:

```
{
  "orderId": "...",
  "status": "COMPLETED"
}
```

---

## Step 5 — Order Status Update

`orderProcessor` consumes the event and updates the order.

```
Order Status → COMPLETED
```

This completes the asynchronous workflow.

---

# Key Design Patterns Used

## 1. Outbox Pattern

Ensures reliable event publishing.

Problem solved:

```
DB write succeeds
Kafka publish fails
→ inconsistent system
```

Outbox guarantees:

```
DB write + event storage = atomic operation
```

Kafka publishing happens afterwards.

---

## 2. Event-Driven Architecture

Services communicate through **events instead of direct API calls**.

Benefits:

* Loose coupling
* Better scalability
* Independent service evolution

---

## 3. Saga-Style Workflow

The system follows a **simple Saga pattern**.

```
Create Order
    ↓
Process Order
    ↓
Update Order Status
```

Each step is handled asynchronously by different services.

---

## 4. Idempotency

Prevents duplicate request processing using the `processed_requests` table.

This protects the system from:

* Network retries
* Duplicate API calls
* Message reprocessing

---

# Technologies Used

* Java
* Spring Boot
* Spring Kafka
* Apache Kafka
* PostgreSQL
* Hibernate / JPA
* Docker
* Jackson (JSON serialization)

---

# What This Phase Achieves

Phase 2 converts the system from a **synchronous REST-only application** into a **reliable event-driven microservice architecture**.

Key improvements:

* Asynchronous order processing
* Reliable event publishing
* Decoupled service communication
* Improved scalability
* Better fault tolerance

---

# Future Improvements (Next Phases)

Possible enhancements:

* Dead Letter Queues (DLQ)
* Kafka retry topics
* Debezium CDC for Outbox
* Event schema versioning
* Distributed tracing
* Observability with OpenTelemetry
* Exactly-once event processing

---

# Summary

In Phase 2, the system evolves into an **event-driven order processing platform** using Kafka and the Outbox pattern.

Orders are created synchronously through REST but processed asynchronously through events, enabling a scalable and reliable distributed architecture.


