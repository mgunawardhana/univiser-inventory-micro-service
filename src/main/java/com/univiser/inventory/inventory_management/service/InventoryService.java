package com.univiser.inventory.inventory_management.service;

import com.univiser.inventory.domain.APIResponse;
import com.univiser.inventory.domain.entity.Item;
import org.springframework.http.ResponseEntity;

public interface InventoryService {

    /**
     * Creates a new item in the inventory.
     *
     * @param item The item to be created.
     * @return A ResponseEntity containing an APIResponse with the result of the operation.
     */
    ResponseEntity<APIResponse> createItem(Item item);

    /**
     * Updates an existing item in the inventory.
     *
     * @param item The item with updated details.
     * @return A ResponseEntity containing an APIResponse with the result of the operation.
     */
    ResponseEntity<APIResponse> updateItem(Item item);

    /**
     * Deletes an item from the inventory.
     *
     * @param item The item to be deleted.
     * @return A ResponseEntity containing an APIResponse with the result of the operation.
     */
    ResponseEntity<APIResponse> deleteItem(Item item);

    /**
     * Retrieves an item from the inventory by its ID.
     *
     * @param id The ID of the item to retrieve.
     * @return A ResponseEntity containing an APIResponse with the item details.
     */
    ResponseEntity<APIResponse> getItemById(Long id);

    /**
     * Retrieves all items from the inventory.
     *
     * @return A ResponseEntity containing an APIResponse with the list of all items.
     */
    ResponseEntity<APIResponse> getAllItems();
}
