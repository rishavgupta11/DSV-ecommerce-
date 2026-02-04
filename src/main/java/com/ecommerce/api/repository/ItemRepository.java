package com.ecommerce.api.repository;

import com.ecommerce.api.model.Item;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

// Repository class for managing Item data in memory
// Uses ArrayList as the in-memory data store
@Repository
public class ItemRepository {

    // In-memory storage using ArrayList
    private final List<Item> items = new ArrayList<>();

    // Auto-incrementing ID generator
    private final AtomicLong idGenerator = new AtomicLong(1);

    public ItemRepository() {
        // Add sample items for demonstration
        initializeSampleData();
    }

    /**
     * Save a new item to the repository
     * @param item Item to save
     * @return Saved item with generated ID
     */
    public Item save(Item item) {
        if (item.getId() == null) {
            item.setId(idGenerator.getAndIncrement());
        }
        items.add(item);
        return item;
    }

    /**
     * Find an item by its ID
     * @param id Item ID
     * @return Optional containing the item if found
     */
    public Optional<Item> findById(Long id) {
        return items.stream()
                .filter(item -> item.getId().equals(id))
                .findFirst();
    }

    /**
     * Find all items
     * @return List of all items
     */
    public List<Item> findAll() {
        return new ArrayList<>(items);
    }

    /**
     * Find items by category
     * @param category Category name
     * @return List of items in the category
     */
    public List<Item> findByCategory(String category) {
        return items.stream()
                .filter(item -> item.getCategory().equalsIgnoreCase(category))
                .toList();
    }

    /**
     * Find items by name (partial match, case-insensitive)
     * @param name Name to search for
     * @return List of matching items
     */
    public List<Item> findByNameContaining(String name) {
        return items.stream()
                .filter(item -> item.getName().toLowerCase().contains(name.toLowerCase()))
                .toList();
    }

    /**
     * Update an existing item
     * @param id Item ID
     * @param updatedItem Updated item data
     * @return Optional containing updated item if found
     */
    public Optional<Item> update(Long id, Item updatedItem) {
        return findById(id).map(existingItem -> {
            items.remove(existingItem);
            updatedItem.setId(id);
            items.add(updatedItem);
            return updatedItem;
        });
    }

    /**
     * Delete an item by ID
     * @param id Item ID
     * @return true if deleted, false if not found
     */
    public boolean deleteById(Long id) {
        return items.removeIf(item -> item.getId().equals(id));
    }

    /**
     * Check if an item exists by SKU
     * @param sku SKU to check
     * @return true if exists
     */
    public boolean existsBySku(String sku) {
        return items.stream()
                .anyMatch(item -> item.getSku().equalsIgnoreCase(sku));
    }

    /**
     * Count total items
     * @return Total number of items
     */
    public long count() {
        return items.size();
    }

    /**
     * Initialize repository with sample data
     */
    private void initializeSampleData() {
        Item laptop = new Item(
                "Dell XPS 15 Laptop",
                "High-performance laptop with Intel i7 processor, 16GB RAM, 512GB SSD",
                new BigDecimal("1299.99"),
                "Electronics",
                "Dell",
                15,
                "DELL-XPS15-2024",
                "https://example.com/images/dell-xps15.jpg"
        );

        Item smartphone = new Item(
                "Samsung Galaxy S24",
                "Latest Samsung flagship smartphone with advanced camera system",
                new BigDecimal("899.99"),
                "Electronics",
                "Samsung",
                25,
                "SAM-S24-BLK",
                "https://example.com/images/samsung-s24.jpg"
        );

        Item headphones = new Item(
                "Sony WH-1000XM5 Headphones",
                "Premium noise-cancelling wireless headphones with superior sound quality",
                new BigDecimal("349.99"),
                "Electronics",
                "Sony",
                30,
                "SONY-WH1000XM5",
                "https://example.com/images/sony-headphones.jpg"
        );

        save(laptop);
        save(smartphone);
        save(headphones);
    }
}