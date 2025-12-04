package tech.hoppr.microservice.inventory.repository;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tech.hoppr.microservice.inventory.repository.InventoryRepository;

@Repository
public interface JpaInventories extends JpaRepository<InventoryEntity, String> {

	@Configuration
	class JpaInventoriesConfiguration {

		@Bean
        InventoryRepository inventoryRepository(JpaRepository<InventoryEntity, String> jpa) {
			return new DbInventoryRepository(jpa);
		}

	}
}
