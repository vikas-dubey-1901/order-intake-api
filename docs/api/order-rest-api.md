# Order Service – REST API Specification

## Base Path

/api/v1


---

## Common Headers

| Header | Required | Description |
|------|----------|-------------|
| `X-Request-Id` | Yes (write APIs) | Idempotency key |
| `X-Correlation-Id` | Yes | Distributed tracing |
| `X-Client-Id` | Yes | Calling application |

Requests missing required headers are rejected.

---

## Order Model (Logical View)

```json
{
  "orderId": "uuid",
  "orderType": "STANDARD | BULK | PROMOTIONAL",
  "channel": "WEB | MOBILE | ADMIN",
  "customerId": "string",
  "status": "RECEIVED | VALIDATED | REJECTED | CANCELLED",
  "currency": "ISO-4217",
  "items": [
    {
      "productId": "string",
      "quantity": 1,
      "unitPrice": 499.50
    }
  ],
  "metadata": {},
  "createdAt": "timestamp",
  "updatedAt": "timestamp"
}

```

## 1. Create Order

### POST /orders

```json
{
  "orderType": "STANDARD",
  "channel": "WEB",
  "customerId": "cust-10291",
  "items": [
    {
      "productId": "prod-501",
      "quantity": 2,
      "unitPrice": 499.50
    }
  ],
  "currency": "INR",
  "metadata": {
    "promoCode": "NEWYEAR2026"
  }
}

```

#### Success Response – 201

```json
{
  "orderId": "uuid",
  "status": "RECEIVED",
  "message": "Order registered successfully"
}

```
## 2. Get Order by ID

### GET /orders/{orderId}

#### Success Response – 200
```json
{
  "orderId": "uuid",
  "orderType": "STANDARD",
  "channel": "WEB",
  "customerId": "cust-10291",
  "status": "VALIDATED",
  "currency": "INR",
  "items": [],
  "metadata": {},
  "createdAt": "timestamp",
  "updatedAt": "timestamp"
}

```

## 3. Search Orders

### GET /orders

#### Query Parameters

- customerId

- status

- channel

- fromDate

- toDate

- page

- size

- sort

#### Success Response – 200

```json
{
  "page": 0,
  "size": 20,
  "totalElements": 125,
  "orders": [
    {
      "orderId": "uuid",
      "status": "RECEIVED",
      "customerId": "cust-10291",
      "createdAt": "timestamp"
    }
  ]
}

```

## 4. Cancel Order

### POST /orders/{orderId}/cancel

#### Success Response – 200

```json
{
  "orderId": "uuid",
  "status": "CANCELLED",
  "message": "Order cancelled successfully"
}

```

## 5. Order Status History

### GET /orders/{orderId}/history

#### Success Response – 200

```json
{
  "orderId": "uuid",
  "history": [
    {
      "status": "RECEIVED",
      "timestamp": "timestamp"
    },
    {
      "status": "VALIDATED",
      "timestamp": "timestamp"
    }
  ]
}

```









