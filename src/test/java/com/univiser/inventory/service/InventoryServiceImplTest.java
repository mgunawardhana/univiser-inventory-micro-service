package com.univiser.inventory.service;

import com.univiser.inventory.domain.APIResponse;
import com.univiser.inventory.domain.entity.Item;
import com.univiser.inventory.inventory_management.repository.InventoryRepo;
import com.univiser.inventory.inventory_management.service.impl.InventoryServiceImpl;
import com.univiser.inventory.util.ResponseUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class InventoryServiceImplTest {

    @Mock
    private InventoryRepo inventoryRepo;

    @Mock
    private ResponseUtil responseUtil;

    @InjectMocks
    private InventoryServiceImpl inventoryService;

    private Item item;
    private APIResponse successApiResponse;
    private APIResponse errorApiResponse;
    private ResponseEntity<APIResponse> successResponse;
    private ResponseEntity<APIResponse> errorResponse;

    @BeforeEach
    void setUp() {
        item = new Item();
        item.setId(1L);
        item.setName("Test Item");
        item.setDescription("Test Description");
        item.setQuantity(10);
        item.setPrice(new BigDecimal("99.99"));
        item.setCreatedAt(LocalDateTime.now());
        item.setUpdatedAt(LocalDateTime.now());

        successApiResponse = APIResponse.builder().statusMessage("Success").statusCode(HttpStatus.OK.name()).result(item).build();

        errorApiResponse = APIResponse.builder().statusMessage("Error").statusCode(HttpStatus.INTERNAL_SERVER_ERROR.name()).errorType("Internal Error").build();

        successResponse = new ResponseEntity<>(successApiResponse, HttpStatus.OK);
        errorResponse = new ResponseEntity<>(errorApiResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Test
    void testCreateItem_Success() {
        when(inventoryRepo.save(any(Item.class))).thenReturn(item);
        when(responseUtil.wrapSuccess(item, HttpStatus.CREATED)).thenReturn(new ResponseEntity<>(successApiResponse, HttpStatus.CREATED));

        ResponseEntity<APIResponse> response = inventoryService.createItem(item);

        assertNotNull(response);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(successApiResponse, response.getBody());
        verify(inventoryRepo, times(1)).save(item);
        verify(responseUtil, times(1)).wrapSuccess(item, HttpStatus.CREATED);
    }

    @Test
    void testUpdateItem_Success() {
        when(inventoryRepo.save(any(Item.class))).thenReturn(item);
        when(responseUtil.wrapSuccess(item, HttpStatus.OK)).thenReturn(successResponse);

        ResponseEntity<APIResponse> response = inventoryService.updateItem(item);

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(successApiResponse, response.getBody());
        verify(inventoryRepo, times(1)).save(item);
        verify(responseUtil, times(1)).wrapSuccess(item, HttpStatus.OK);
    }

    @Test
    void testDeleteItem_Success() {
        doNothing().when(inventoryRepo).delete(any(Item.class));
        when(responseUtil.wrapSuccess(item, HttpStatus.OK)).thenReturn(successResponse);

        ResponseEntity<APIResponse> response = inventoryService.deleteItem(item);

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(successApiResponse, response.getBody());
        verify(inventoryRepo, times(1)).delete(item);
        verify(responseUtil, times(1)).wrapSuccess(item, HttpStatus.OK);
    }

    @Test
    void testGetItemById_Success() {
        when(inventoryRepo.findById(1L)).thenReturn(Optional.of(item));
        when(responseUtil.wrapSuccess(item, HttpStatus.OK)).thenReturn(successResponse);

        ResponseEntity<APIResponse> response = inventoryService.getItemById(1L);

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(successApiResponse, response.getBody());
        verify(inventoryRepo, times(1)).findById(1L);
        verify(responseUtil, times(1)).wrapSuccess(item, HttpStatus.OK);
    }

    @Test
    void testGetItemById_NotFound() {
        when(inventoryRepo.findById(1L)).thenReturn(Optional.empty());
        when(responseUtil.wrapSuccess(null, HttpStatus.OK)).thenReturn(successResponse);

        ResponseEntity<APIResponse> response = inventoryService.getItemById(1L);

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(inventoryRepo, times(1)).findById(1L);
        verify(responseUtil, times(1)).wrapSuccess(null, HttpStatus.OK);
    }

    @Test
    void testGetAllItems_Success() {
        List<Item> items = Collections.singletonList(item);
        when(inventoryRepo.findAll()).thenReturn(items);
        when(responseUtil.wrapSuccess(items, HttpStatus.OK)).thenReturn(successResponse);

        ResponseEntity<APIResponse> response = inventoryService.getAllItems();

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(successApiResponse, response.getBody());
        verify(inventoryRepo, times(1)).findAll();
        verify(responseUtil, times(1)).wrapSuccess(items, HttpStatus.OK);
    }

    @Test
    void testCreateItem_Exception() {
        RuntimeException exception = new RuntimeException("Database error");
        when(inventoryRepo.save(any(Item.class))).thenThrow(exception);
        when(responseUtil.wrapError("Error creating Item!", exception.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR)).thenReturn(errorResponse);

        ResponseEntity<APIResponse> response = inventoryService.createItem(item);

        assertNotNull(response);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertEquals(errorApiResponse, response.getBody());
        verify(inventoryRepo, times(1)).save(item);
        verify(responseUtil, times(1)).wrapError("Error creating Item!", exception.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Test
    void testUpdateItem_Exception() {
        RuntimeException exception = new RuntimeException("Database error");
        when(inventoryRepo.save(any(Item.class))).thenThrow(exception);
        when(responseUtil.wrapError("Error updating item!", exception.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR)).thenReturn(errorResponse);

        ResponseEntity<APIResponse> response = inventoryService.updateItem(item);

        assertNotNull(response);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertEquals(errorApiResponse, response.getBody());
        verify(inventoryRepo, times(1)).save(item);
        verify(responseUtil, times(1)).wrapError("Error updating item!", exception.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Test
    void testDeleteItem_Exception() {
        RuntimeException exception = new RuntimeException("Database error");
        doThrow(exception).when(inventoryRepo).delete(any(Item.class));
        when(responseUtil.wrapError("Error deleting item!", exception.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR)).thenReturn(errorResponse);

        ResponseEntity<APIResponse> response = inventoryService.deleteItem(item);

        assertNotNull(response);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertEquals(errorApiResponse, response.getBody());
        verify(inventoryRepo, times(1)).delete(item);
        verify(responseUtil, times(1)).wrapError("Error deleting item!", exception.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Test
    void testGetItemById_Exception() {
        RuntimeException exception = new RuntimeException("Database error");
        when(inventoryRepo.findById(1L)).thenThrow(exception);
        when(responseUtil.wrapError("Error getting item!", exception.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR)).thenReturn(errorResponse);

        ResponseEntity<APIResponse> response = inventoryService.getItemById(1L);

        assertNotNull(response);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertEquals(errorApiResponse, response.getBody());
        verify(inventoryRepo, times(1)).findById(1L);
        verify(responseUtil, times(1)).wrapError("Error getting item!", exception.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Test
    void testGetAllItems_Exception() {
        RuntimeException exception = new RuntimeException("Database error");
        when(inventoryRepo.findAll()).thenThrow(exception);
        when(responseUtil.wrapError("Error getting items!", exception.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR)).thenReturn(errorResponse);

        ResponseEntity<APIResponse> response = inventoryService.getAllItems();

        assertNotNull(response);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertEquals(errorApiResponse, response.getBody());
        verify(inventoryRepo, times(1)).findAll();
        verify(responseUtil, times(1)).wrapError("Error getting items!", exception.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}