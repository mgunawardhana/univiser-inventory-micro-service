package com.univiser.inventory.inventory_management.service.impl;

import com.univiser.inventory.domain.APIResponse;
import com.univiser.inventory.domain.entity.Item;
import com.univiser.inventory.inventory_management.repository.InventoryRepo;
import com.univiser.inventory.inventory_management.service.InventoryService;
import com.univiser.inventory.util.ResponseUtil;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class InventoryServiceImpl implements InventoryService {

    @NonNull
    private final InventoryRepo inventoryRepo;

    @NonNull
    private final ResponseUtil responseUtil;

    public InventoryServiceImpl(@NonNull InventoryRepo inventoryRepo, @NonNull ResponseUtil responseUtil) {
        this.inventoryRepo = inventoryRepo;
        this.responseUtil = responseUtil;
    }

    @Override
    public ResponseEntity<APIResponse> createItem(Item item) {
        try {
            Item save = inventoryRepo.save(item);
            log.info("Item created: {}", save);
            return responseUtil.wrapSuccess(save, HttpStatus.CREATED);
        } catch (Exception e) {
            log.error("Error creating item: {}", e.getMessage());
            return responseUtil.wrapError("Error creating Item!", e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseEntity<APIResponse> updateItem(Item item) {
        try {
            Item save = inventoryRepo.save(item);
            log.info("Item updated: {}", save);
            return responseUtil.wrapSuccess(save, HttpStatus.OK);
        } catch (Exception e) {
            log.error("Error updating item: {}", e.getMessage());
            return responseUtil.wrapError("Error updating item!", e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseEntity<APIResponse> deleteItem(Item item) {
        try {
            inventoryRepo.delete(item);
            log.info("Item deleted: {}", item);
            return responseUtil.wrapSuccess(item, HttpStatus.OK);
        } catch (Exception e) {
            log.error("Error deleting item: {}", e.getMessage());
            return responseUtil.wrapError("Error deleting item!", e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseEntity<APIResponse> getItemById(Long id) {
        try {
            Item item = inventoryRepo.findById(id).orElse(null);
            return responseUtil.wrapSuccess(item, HttpStatus.OK);
        } catch (Exception e) {
            log.error("Error getting item: {}", e.getMessage());
            return responseUtil.wrapError("Error getting item!", e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseEntity<APIResponse> getAllItems() {
        try {
            List<Item> items = inventoryRepo.findAll();
            return responseUtil.wrapSuccess(items, HttpStatus.OK);
        } catch (Exception e) {
            log.error("Error getting items: {}", e.getMessage());
            return responseUtil.wrapError("Error getting items!", e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
