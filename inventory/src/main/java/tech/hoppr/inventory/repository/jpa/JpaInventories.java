package tech.hoppr.inventory.repository.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tech.hoppr.inventory.repository.jpa.InventoryEntity;

@Repository
public interface JpaInventories extends JpaRepository<InventoryEntity, String> {
}
