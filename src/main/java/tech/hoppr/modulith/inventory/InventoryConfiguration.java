package tech.hoppr.modulith.inventory;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import tech.hoppr.modulith.inventory.repository.InventoryQueries;
import tech.hoppr.modulith.inventory.repository.InventoryRepository;
import tech.hoppr.modulith.inventory.repository.JpaInventories;
import tech.hoppr.modulith.inventory.service.InventoryService;

@Configuration
public class InventoryConfiguration {

	@Bean
	InventoryService inventoryService(InventoryRepository inventoryRepository) {
		return new InventoryService(inventoryRepository);
	}

	@Bean
	InventoryQueries inventoryQueries(JpaInventories jpa) {
		return new InventoryQueries(jpa);
	}

}
