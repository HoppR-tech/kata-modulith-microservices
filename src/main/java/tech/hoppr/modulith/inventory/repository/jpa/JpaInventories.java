package tech.hoppr.modulith.inventory.repository.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JpaInventories extends JpaRepository<InventoryEntity, String> {
}
