
# Order Lifecycle

## Order States

| State | Description |
|-----|-------------|
| `RECEIVED` | Order accepted |
| `VALIDATED` | Business validation passed |
| `REJECTED` | Validation failed |
| `CANCELLED` | Order cancelled |

---

## State Transitions

###### RECEIVED → VALIDATED
###### RECEIVED → REJECTED
###### RECEIVED → CANCELLED
###### VALIDATED → CANCELLED


#### Invalid transitions are rejected.

---

## Asynchronous Processing

- Orders are accepted before full validation completes
- Downstream services consume order events independently
- REST clients must not assume finality from initial responses

