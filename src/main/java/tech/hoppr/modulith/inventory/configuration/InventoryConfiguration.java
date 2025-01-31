package tech.hoppr.modulith.inventory.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.JpaRepository;
import tech.hoppr.modulith.inventory.repository.InventoryRepository;
import tech.hoppr.modulith.inventory.repository.jpa.DbInventoryRepository;
import tech.hoppr.modulith.inventory.repository.jpa.InventoryEntity;
import tech.hoppr.modulith.inventory.service.InventoryService;

@Configuration
public class InventoryConfiguration {

	@Bean
	InventoryRepository inventoryRepository(JpaRepository<InventoryEntity, String> jpa) {
		return new DbInventoryRepository(jpa);
	}

	@Bean
	InventoryService inventoryService(InventoryRepository inventoryRepository) {
		return new InventoryService(inventoryRepository);
	}

}
