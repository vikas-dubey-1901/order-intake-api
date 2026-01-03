
# Error Model

## Error Response Format

All errors follow a consistent structure.

```json
{
  "errorCode": "STRING",
  "message": "Human-readable summary",
  "details": []
}

```

### Common Error Codes

| Code                      | HTTP | Description               |
| ------------------------- | ---- | ------------------------- |
| `ORDER_VALIDATION_FAILED` | 400  | Invalid request data      |
| `DUPLICATE_REQUEST`       | 409  | Duplicate X-Request-Id    |
| `ORDER_NOT_FOUND`         | 404  | Order does not exist      |
| `ORDER_STATE_INVALID`     | 409  | Invalid state transition  |
| `INTERNAL_ERROR`          | 500  | Unexpected server failure |

