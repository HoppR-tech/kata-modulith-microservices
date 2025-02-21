package tech.hoppr.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import tech.hoppr.listener.OrderCancelledListener;
import tech.hoppr.listener.OrderPlacedListener;
import tech.hoppr.repository.InventoryRepository;
import tech.hoppr.repository.jpa.JpaInventories;
import tech.hoppr.repository.jpa.DbInventoryRepository;
import tech.hoppr.service.InventoryService;

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

	@Bean
	OrderCancelledListener orderCancelledListener(InventoryService inventoryService) {
		return new OrderCancelledListener(inventoryService);
	}

}
