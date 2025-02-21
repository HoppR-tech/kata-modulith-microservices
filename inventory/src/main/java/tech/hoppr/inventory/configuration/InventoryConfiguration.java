package tech.hoppr.inventory.configuration;

import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import tech.hoppr.inventory.ports.in.OrderListener;
import tech.hoppr.inventory.repository.InventoryRepository;
import tech.hoppr.inventory.repository.jpa.DbInventoryRepository;
import tech.hoppr.inventory.repository.jpa.JpaInventories;
import tech.hoppr.inventory.service.InventoryService;

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
	OrderListener orderListener(InventoryService inventoryService) {
		return new OrderListener(inventoryService);
	}

	@Bean
	MessageConverter jsonMessageConverter() {
		return new Jackson2JsonMessageConverter();
	}

}
