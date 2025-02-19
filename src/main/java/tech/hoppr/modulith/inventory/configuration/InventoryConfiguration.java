package tech.hoppr.modulith.inventory.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import tech.hoppr.modulith.inventory.listener.OrderPlacedListener;
import tech.hoppr.modulith.inventory.repository.jpa.DbInventoryRepository;
import tech.hoppr.modulith.inventory.repository.InventoryRepository;
import tech.hoppr.modulith.inventory.repository.jpa.JpaInventories;
import tech.hoppr.modulith.inventory.service.InventoryService;

@Configuration
public class InventoryConfiguration {

	@Bean
	InventoryRepository inventoryRepository(JpaInventories jpa) {
		return new DbInventoryRepository(jpa);
	}

	@Bean
	InventoryService inventoryService(InventoryRepository inventoryRepository) {
		return new InventoryService(inventoryRepository);
	}

	@Bean
	OrderPlacedListener orderPlacedListener(InventoryService inventoryService) {
		return new OrderPlacedListener(inventoryService);
	}

}
