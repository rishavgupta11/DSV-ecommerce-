package com.ecommerce.api.model;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;


// Item Model representing a product in the e-commerce system
// Contains all essential product information including pricing, inventory, and metadata
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Item {

    private Long id;

    // Name of the item/product
    @NotBlank(message = "Item name is required")
    @Size(min = 3, max = 100, message = "Item name must be between 3 and 100 characters")
    private String name;

    // Detailed description of the item
    @NotBlank(message = "Description is required")
    @Size(min = 10, max = 500, message = "Description must be between 10 and 500 characters")
    private String description;

    //Price of the item
    @NotNull(message = "Price is required")
    @DecimalMin(value = "0.01", message = "Price must be greater than 0")
    @DecimalMax(value = "999999.99", message = "Price must be less than 1,000,000")
    private BigDecimal price;


    //Category of the item (e.g., Electronics, Clothing, Books)
    @NotBlank(message = "Category is required")
    private String category;

    private String brand;

    //Quantity available in stock
    @NotNull(message = "Stock quantity is required")
    @Min(value = 0, message = "Stock quantity cannot be negative")
    private Integer stockQuantity;

    //SKU (Stock Keeping Unit) - unique product identifier
    @NotBlank(message = "SKU is required")
    @Pattern(regexp = "^[A-Z0-9-]+$", message = "SKU must contain only uppercase letters, numbers, and hyphens")
    private String sku;

    private String imageUrl;

    private Boolean active;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    public Item(String name, String description, BigDecimal price, String category,
                String brand, Integer stockQuantity, String sku, String imageUrl) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.category = category;
        this.brand = brand;
        this.stockQuantity = stockQuantity;
        this.sku = sku;
        this.imageUrl = imageUrl;
        this.active = true;
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }
}