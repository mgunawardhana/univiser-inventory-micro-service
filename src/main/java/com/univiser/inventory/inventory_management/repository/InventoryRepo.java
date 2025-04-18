package com.univiser.inventory.inventory_management.repository;

import com.univiser.inventory.domain.entity.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InventoryRepo extends JpaRepository<Item, Long> {
}
