package com.ecommerce.api.service;

import com.ecommerce.api.exception.DuplicateSkuException;
import com.ecommerce.api.exception.ItemNotFoundException;
import com.ecommerce.api.model.Item;
import com.ecommerce.api.repository.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;


// Service layer for Item operations
// Contains business logic for managing items
@Service
public class ItemService {

    private final ItemRepository itemRepository;

    @Autowired
    public ItemService(ItemRepository itemRepository) {
        this.itemRepository = itemRepository;
    }

    /**
     * Create a new item
     * Validates that SKU is unique before creating
     * @param item Item to create
     * @return Created item
     * @throws DuplicateSkuException if SKU already exists
     */
    public Item createItem(Item item) {
        // Check if SKU already exists
        if (itemRepository.existsBySku(item.getSku())) {
            throw new DuplicateSkuException(item.getSku());
        }

        // Set timestamps
        item.setCreatedAt(LocalDateTime.now());
        item.setUpdatedAt(LocalDateTime.now());

        // Set active status if not specified
        if (item.getActive() == null) {
            item.setActive(true);
        }

        return itemRepository.save(item);
    }

    /**
     * Get an item by ID
     * @param id Item ID
     * @return Item
     * @throws ItemNotFoundException if item not found
     */
    public Item getItemById(Long id) {
        return itemRepository.findById(id)
                .orElseThrow(() -> new ItemNotFoundException(id));
    }

    /**
     * Get all items
     * @return List of all items
     */
    public List<Item> getAllItems() {
        return itemRepository.findAll();
    }

    /**
     * Get items by category
     * @param category Category name
     * @return List of items in the category
     */
    public List<Item> getItemsByCategory(String category) {
        return itemRepository.findByCategory(category);
    }

    /**
     * Search items by name
     * @param name Name to search for
     * @return List of matching items
     */
    public List<Item> searchItemsByName(String name) {
        return itemRepository.findByNameContaining(name);
    }

    /**
     * Update an existing item
     * @param id Item ID
     * @param updatedItem Updated item data
     * @return Updated item
     * @throws ItemNotFoundException if item not found
     */
    public Item updateItem(Long id, Item updatedItem) {
        Item existingItem = getItemById(id);

        // Check if SKU is being changed and if new SKU already exists
        if (!existingItem.getSku().equals(updatedItem.getSku())
                && itemRepository.existsBySku(updatedItem.getSku())) {
            throw new DuplicateSkuException(updatedItem.getSku());
        }

        // Preserve created timestamp
        updatedItem.setCreatedAt(existingItem.getCreatedAt());
        updatedItem.setUpdatedAt(LocalDateTime.now());

        return itemRepository.update(id, updatedItem)
                .orElseThrow(() -> new ItemNotFoundException(id));
    }

    /**
     * Delete an item
     * @param id Item ID
     * @throws ItemNotFoundException if item not found
     */
    public void deleteItem(Long id) {
        if (!itemRepository.deleteById(id)) {
            throw new ItemNotFoundException(id);
        }
    }

    /**
     * Update item stock quantity
     * @param id Item ID
     * @param quantity New quantity
     * @return Updated item
     */
    public Item updateStock(Long id, Integer quantity) {
        Item item = getItemById(id);
        item.setStockQuantity(quantity);
        item.setUpdatedAt(LocalDateTime.now());
        return itemRepository.update(id, item)
                .orElseThrow(() -> new ItemNotFoundException(id));
    }

    /**
     * Get total item count
     * @return Total number of items
     */
    public long getItemCount() {
        return itemRepository.count();
    }
}