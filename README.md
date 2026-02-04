# E-Commerce REST API

A RESTful API for managing e-commerce items built with Spring Boot and Java 17. This application provides a complete backend solution for product management with in-memory data storage.

## Features

- ✅ **RESTful API Design** - Following REST principles
- ✅ **CRUD Operations** - Create, Read, Update, Delete items
- ✅ **Input Validation** - Comprehensive validation with meaningful error messages
- ✅ **In-Memory Storage** - ArrayList-based data storage
- ✅ **Exception Handling** - Global exception handling with detailed error responses
- ✅ **Sample Data** - Pre-loaded with sample items for testing
- ✅ **CORS Enabled** - Cross-Origin Resource Sharing support

## Technology Stack

- **Java 17**
- **Spring Boot 3.2.0**
- **Spring Web**
- **Spring Validation**
- **Lombok** (for reducing boilerplate code)
- **Maven** (build tool)

## Project Structure

```
ecommerce-api/
├── src/
│   └── main/
│       ├── java/com/ecommerce/api/
│       │   ├── controller/      # REST Controllers
│       │   │   └── ItemController.java
│       │   ├── service/         # Business Logic
│       │   │   └── ItemService.java
│       │   ├── repository/      # Data Access Layer
│       │   │   └── ItemRepository.java
│       │   ├── model/           # Domain Models
│       │   │   └── Item.java
│       │   ├── dto/             # Data Transfer Objects
│       │   │   ├── ApiResponse.java
│       │   │   └── ErrorResponse.java
│       │   ├── exception/       # Exception Handling
│       │   │   ├── GlobalExceptionHandler.java
│       │   │   ├── ItemNotFoundException.java
│       │   │   └── DuplicateSkuException.java
│       │   └── EcommerceApiApplication.java
│       └── resources/
│           └── application.properties
└── pom.xml
```

## API Endpoints

### Base URL
```
http://localhost:8080/api/items
```

### 1. Create a New Item
**POST** `/api/items`

**Request Body:**
```json
{
  "name": "iPhone 15 Pro",
  "description": "Latest iPhone with advanced camera system and A17 Pro chip",
  "price": 999.99,
  "category": "Electronics",
  "brand": "Apple",
  "stockQuantity": 50,
  "sku": "APPLE-IP15PRO-256",
  "imageUrl": "https://example.com/images/iphone15pro.jpg"
}
```

**Response (201 Created):**
```json
{
  "success": true,
  "message": "Item created successfully",
  "data": {
    "id": 4,
    "name": "iPhone 15 Pro",
    "description": "Latest iPhone with advanced camera system and A17 Pro chip",
    "price": 999.99,
    "category": "Electronics",
    "brand": "Apple",
    "stockQuantity": 50,
    "sku": "APPLE-IP15PRO-256",
    "imageUrl": "https://example.com/images/iphone15pro.jpg",
    "active": true,
    "createdAt": "2024-02-04T10:30:00",
    "updatedAt": "2024-02-04T10:30:00"
  },
  "timestamp": "2024-02-04T10:30:00"
}
```

### 2. Get Item by ID
**GET** `/api/items/{id}`

**Example:** `GET /api/items/1`

**Response (200 OK):**
```json
{
  "success": true,
  "message": "Item retrieved successfully",
  "data": {
    "id": 1,
    "name": "Dell XPS 15 Laptop",
    "description": "High-performance laptop with Intel i7 processor, 16GB RAM, 512GB SSD",
    "price": 1299.99,
    "category": "Electronics",
    "brand": "Dell",
    "stockQuantity": 15,
    "sku": "DELL-XPS15-2024",
    "imageUrl": "https://example.com/images/dell-xps15.jpg",
    "active": true,
    "createdAt": "2024-02-04T09:00:00",
    "updatedAt": "2024-02-04T09:00:00"
  },
  "timestamp": "2024-02-04T10:35:00"
}
```

### 3. Get All Items
**GET** `/api/items`

**Response (200 OK):**
```json
{
  "success": true,
  "message": "Retrieved 3 items",
  "data": [
    {
      "id": 1,
      "name": "Dell XPS 15 Laptop"
    },
    {
      "id": 2,
      "name": "Samsung Galaxy S24"
    },
    {
      "id": 3,
      "name": "Sony WH-1000XM5 Headphones"
    }
  ],
  "timestamp": "2024-02-04T10:40:00"
}
```

### 4. Get Items by Category
**GET** `/api/items/category/{category}`

**Example:** `GET /api/items/category/Electronics`

### 5. Search Items by Name
**GET** `/api/items/search?name={searchTerm}`

**Example:** `GET /api/items/search?name=laptop`

### 6. Update an Item
**PUT** `/api/items/{id}`

**Request Body:** (Same as Create Item)

### 7. Delete an Item
**DELETE** `/api/items/{id}`

**Response (200 OK):**
```json
{
  "success": true,
  "message": "Item deleted successfully",
  "data": null,
  "timestamp": "2024-02-04T10:50:00"
}
```

### 8. Update Stock Quantity
**PATCH** `/api/items/{id}/stock`

**Request Body:**
```json
{
  "quantity": 100
}
```

### 9. Get Total Item Count
**GET** `/api/items/stats/count`

**Response (200 OK):**
```json
{
  "success": true,
  "message": "Item count retrieved",
  "data": {
    "totalItems": 3
  },
  "timestamp": "2024-02-04T11:00:00"
}
```

## Error Responses

### Validation Error (400 Bad Request)
```json
{
  "timestamp": "2024-02-04T11:05:00",
  "status": 400,
  "error": "Validation Failed",
  "message": "Input validation failed. Please check the errors below.",
  "path": "/api/items",
  "validationErrors": {
    "name": "Item name must be between 3 and 100 characters",
    "price": "Price is required"
  }
}
```

### Item Not Found (404 Not Found)
```json
{
  "timestamp": "2024-02-04T11:10:00",
  "status": 404,
  "error": "Not Found",
  "message": "Item not found with id: 999",
  "path": "/api/items/999"
}
```

### Duplicate SKU (409 Conflict)
```json
{
  "timestamp": "2024-02-04T11:15:00",
  "status": 409,
  "error": "Conflict",
  "message": "Item with SKU 'DELL-XPS15-2024' already exists",
  "path": "/api/items"
}
```

## How to Run the Application

### Prerequisites
- Java 17 or higher
- Maven 3.6 or higher

### Running Locally

1. **Clone/Download the project**
   ```bash
   cd ecommerce-api
   ```

2. **Build the project**
   ```bash
   mvn clean install
   ```

3. **Run the application**
   ```bash
   mvn spring-boot:run
   ```

   Or run the JAR file:
   ```bash
   java -jar target/ecommerce-api-1.0.0.jar
   ```

4. **Access the API**
    - The application will start on `http://localhost:8080`
    - Test the API at `http://localhost:8080/api/items`

### Testing with cURL

**Create an Item:**
```bash
curl -X POST http://localhost:8080/api/items \
  -H "Content-Type: application/json" \
  -d '{
    "name": "MacBook Pro",
    "description": "Powerful laptop for professionals",
    "price": 2499.99,
    "category": "Electronics",
    "brand": "Apple",
    "stockQuantity": 20,
    "sku": "APPLE-MBP-16-2024",
    "imageUrl": "https://example.com/macbook.jpg"
  }'
```

**Get All Items:**
```bash
curl http://localhost:8080/api/items
```

**Get Item by ID:**
```bash
curl http://localhost:8080/api/items/1
```

**Search Items:**
```bash
curl "http://localhost:8080/api/items/search?name=laptop"
```

**Update Stock:**
```bash
curl -X PATCH http://localhost:8080/api/items/1/stock \
  -H "Content-Type: application/json" \
  -d '{"quantity": 50}'
```

**Delete Item:**
```bash
curl -X DELETE http://localhost:8080/api/items/1
```

## Testing with Postman

1. Import the following endpoints into Postman
2. Set the base URL to `http://localhost:8080`
3. Use the examples above for request bodies
4. The API returns JSON responses with proper HTTP status codes

## Implementation Details

### In-Memory Data Storage
- Uses `ArrayList<Item>` for storing items in memory
- `AtomicLong` for thread-safe ID generation
- Data resets on application restart
- Pre-loaded with 3 sample items for testing

### Validation
- Bean Validation (JSR 380) annotations on the Item model
- Custom validation for SKU format (uppercase, numbers, hyphens)
- Comprehensive error messages for all validation failures

### Exception Handling
- `GlobalExceptionHandler` for centralized error handling
- Custom exceptions: `ItemNotFoundException`, `DuplicateSkuException`
- Consistent error response format across all endpoints

### Sample Data
The application comes pre-loaded with:
1. Dell XPS 15 Laptop (ID: 1)
2. Samsung Galaxy S24 (ID: 2)
3. Sony WH-1000XM5 Headphones (ID: 3)


## Future Enhancements

- [ ] Add database persistence (PostgreSQL/MySQL)
- [ ] Implement authentication and authorization
- [ ] Add pagination for item lists
- [ ] Implement sorting and advanced filtering
- [ ] Add image upload functionality
- [ ] Implement order management
- [ ] Add user reviews and ratings
- [ ] Implement shopping cart functionality
- [ ] Add OpenAPI/Swagger documentation
- [ ] Implement caching for better performance

## Contributing

Feel free to submit issues or pull requests for improvements!

## License

This project is created for company task purposes.

## Contact

Email : rishav.mh103@gmail.com
For questions or feedback, please create an issue in the repository.
