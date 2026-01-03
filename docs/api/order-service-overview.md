# Order Service – Overview

## Purpose

The Order Service acts as the entry point into the order domain.  
It accepts orders from synchronous REST clients and asynchronous event-driven systems, validates them, persists their state, and publishes domain events for downstream processing.

## Responsibilities

- Accept orders via REST and Kafka
- Perform structural and business validation
- Ensure idempotent processing
- Persist order state and audit data
- Publish order lifecycle events

## Out of Scope

The following concerns are intentionally excluded from this service:
- Payment processing
- Inventory management
- Shipping and logistics
- Customer notifications

These are handled by independent downstream services.

## Consumers

- Web storefront
- Mobile applications
- Internal admin tools
- Partner and integration platforms

## Architecture Notes

- REST is used for command-based order intake and querying
- Kafka is used for asynchronous communication
- Order processing is partially asynchronous by design
- The service follows a command–query separation model
