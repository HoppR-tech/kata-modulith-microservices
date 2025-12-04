package tech.hoppr.microservice.inventory;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import tech.hoppr.microservice.inventory.repository.InventoryQueries;
import tech.hoppr.microservice.inventory.repository.InventoryRepository;
import tech.hoppr.microservice.inventory.repository.JpaInventories;
import tech.hoppr.microservice.inventory.service.InventoryService;
import tech.hoppr.microservice.inventory.shared.MessageEmitter;
import tech.hoppr.microservice.inventory.shared.SpringMessageEmitter;

import java.time.Clock;

@SpringBootApplication
public class InventoryApplication {

	public static void main(String[] args) {
		SpringApplication.run(InventoryApplication.class, args);
	}

	@Configuration
	public static class SharedConfiguration {

		@ConditionalOnMissingBean(Clock.class)
		@Bean
		Clock clock() {
			return Clock.systemUTC();
		}

		@Bean
		MessageEmitter messageEmitter(ApplicationEventPublisher publisher) {
			return new SpringMessageEmitter(publisher);
		}

		@Bean
		InventoryService inventoryService(InventoryRepository inventoryRepository) {
			return new InventoryService(inventoryRepository);
		}

		@Bean
		InventoryQueries inventoryQueries(JpaInventories jpa) {
			return new InventoryQueries(jpa);
		}

	}

}
