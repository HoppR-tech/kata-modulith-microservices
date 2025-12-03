package tech.hoppr.modulith.inventory.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tech.hoppr.modulith.inventory.entity.InventoryEntity;

@Repository
public interface JpaInventories extends JpaRepository<InventoryEntity, String> {
}
