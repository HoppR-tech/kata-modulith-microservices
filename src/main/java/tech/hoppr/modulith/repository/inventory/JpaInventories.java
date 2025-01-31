package tech.hoppr.modulith.repository.inventory;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tech.hoppr.modulith.entity.InventoryEntity;

@Repository
public interface JpaInventories extends JpaRepository<InventoryEntity, String> {
}
