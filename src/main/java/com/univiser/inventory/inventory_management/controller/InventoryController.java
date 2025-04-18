package com.univiser.inventory.inventory_management.controller;

import com.univiser.inventory.domain.APIResponse;
import com.univiser.inventory.domain.entity.Item;
import com.univiser.inventory.inventory_management.repository.InventoryRepo;
import com.univiser.inventory.inventory_management.service.InventoryService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("api/v1/inventory-management")
@RequiredArgsConstructor
public class InventoryController {

    @NonNull
    private final InventoryService inventoryService;

    @PostMapping
    public ResponseEntity<APIResponse> createItem(@RequestBody Item item) {
        log.info("Creating new inventory item {}", item);
        var response = inventoryService.createItem(item);
        log.info("Successfully created inventory item {}", item);
        return response;
    }

    @PutMapping
    public ResponseEntity<APIResponse> updateItem(@RequestBody Item item) {
        log.info("Updating inventory item {}", item);
        var response = inventoryService.updateItem(item);
        log.info("Successfully updated inventory item {}", item);
        return response;
    }

    @DeleteMapping
    public ResponseEntity<APIResponse> deleteItem(@RequestBody Item item) {
        log.info("Deleting inventory item {}", item);
        var response = inventoryService.deleteItem(item);
        log.info("Successfully deleted inventory item {}", item);
        return response;
    }

    @GetMapping("/{id}")
    public ResponseEntity<APIResponse> getItemById(@PathVariable Long id) {
        log.info("Fetching inventory item by ID {}", id);
        var response = inventoryService.getItemById(id);
        log.info("Successfully fetched inventory item by ID {}", id);
        return response;
    }

    @GetMapping("/fetch-all")
    public ResponseEntity<APIResponse> getAllItems() {
        log.info("Fetching all inventory items");
        var response = inventoryService.getAllItems();
        log.info("Successfully fetched all inventory items");
        return response;
    }

}
