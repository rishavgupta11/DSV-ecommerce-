package com.ecommerce.api.controller;

import com.ecommerce.api.dto.ApiResponse;
import com.ecommerce.api.model.Item;
import com.ecommerce.api.service.ItemService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

// REST Controller for Item management
// Provides RESTful API endpoints for CRUD operations on items
@RestController
@RequestMapping("/api/items")
@CrossOrigin(origins = "*") // Enable CORS for all origins
public class ItemController {

    private final ItemService itemService;

    @Autowired
    public ItemController(ItemService itemService) {
        this.itemService = itemService;
    }


    // Create a new item
    // POST /api/items
    @PostMapping
    public ResponseEntity<ApiResponse<Item>> createItem(@Valid @RequestBody Item item) {
        Item createdItem = itemService.createItem(item);
        ApiResponse<Item> response = ApiResponse.success("Item created successfully", createdItem);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }


     // Get a single item by ID
     // GET /api/items/{id}
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<Item>> getItemById(@PathVariable Long id) {
        Item item = itemService.getItemById(id);
        ApiResponse<Item> response = ApiResponse.success("Item retrieved successfully", item);
        return ResponseEntity.ok(response);
    }


    // Get all items
    // GET /api/items
    @GetMapping
    public ResponseEntity<ApiResponse<List<Item>>> getAllItems() {
        List<Item> items = itemService.getAllItems();
        ApiResponse<List<Item>> response = ApiResponse.success(
                "Retrieved " + items.size() + " items", items);
        return ResponseEntity.ok(response);
    }


     // Search items by category
     // GET /api/items/category/{category}
    @GetMapping("/category/{category}")
    public ResponseEntity<ApiResponse<List<Item>>> getItemsByCategory(@PathVariable String category) {
        List<Item> items = itemService.getItemsByCategory(category);
        ApiResponse<List<Item>> response = ApiResponse.success(
                "Retrieved " + items.size() + " items in category: " + category, items);
        return ResponseEntity.ok(response);
    }


     // Search items by name
     // GET /api/items/search?name={name}
    @GetMapping("/search")
    public ResponseEntity<ApiResponse<List<Item>>> searchItems(@RequestParam String name) {
        List<Item> items = itemService.searchItemsByName(name);
        ApiResponse<List<Item>> response = ApiResponse.success(
                "Found " + items.size() + " items matching: " + name, items);
        return ResponseEntity.ok(response);
    }


     // Update an existing item
     // PUT /api/items/{id}
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<Item>> updateItem(
            @PathVariable Long id,
            @Valid @RequestBody Item item) {
        Item updatedItem = itemService.updateItem(id, item);
        ApiResponse<Item> response = ApiResponse.success("Item updated successfully", updatedItem);
        return ResponseEntity.ok(response);
    }


    // Delete an item
    // DELETE /api/items/{id}
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteItem(@PathVariable Long id) {
        itemService.deleteItem(id);
        ApiResponse<Void> response = ApiResponse.success("Item deleted successfully", null);
        return ResponseEntity.ok(response);
    }


    // Update item stock quantity
    // PATCH /api/items/{id}/stock
    @PatchMapping("/{id}/stock")
    public ResponseEntity<ApiResponse<Item>> updateStock(
            @PathVariable Long id,
            @RequestBody Map<String, Integer> stockUpdate) {
        Integer newQuantity = stockUpdate.get("quantity");
        if (newQuantity == null || newQuantity < 0) {
            return ResponseEntity.badRequest()
                    .body(ApiResponse.error("Invalid quantity value"));
        }

        Item updatedItem = itemService.updateStock(id, newQuantity);
        ApiResponse<Item> response = ApiResponse.success("Stock updated successfully", updatedItem);
        return ResponseEntity.ok(response);
    }


    // Get total item count
    // GET /api/items/stats/count
    @GetMapping("/stats/count")
    public ResponseEntity<ApiResponse<Map<String, Long>>> getItemCount() {
        long count = itemService.getItemCount();
        Map<String, Long> stats = Map.of("totalItems", count);
        ApiResponse<Map<String, Long>> response = ApiResponse.success("Item count retrieved", stats);
        return ResponseEntity.ok(response);
    }
}