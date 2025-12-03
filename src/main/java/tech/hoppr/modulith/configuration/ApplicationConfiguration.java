package tech.hoppr.modulith.configuration;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.JpaRepository;
import tech.hoppr.modulith.inventory.entity.InventoryEntity;
import tech.hoppr.modulith.inventory.repository.DbInventoryRepository;
import tech.hoppr.modulith.inventory.repository.InventoryRepository;
import tech.hoppr.modulith.inventory.service.InventoryService;
import tech.hoppr.modulith.order.entity.OrderEntity;
import tech.hoppr.modulith.order.model.OrderId;
import tech.hoppr.modulith.order.repository.DbOrderRepository;
import tech.hoppr.modulith.order.repository.OrderRepository;
import tech.hoppr.modulith.order.service.OrderFactory;
import tech.hoppr.modulith.order.service.OrderService;

@Configuration
public class ApplicationConfiguration {

	@PersistenceContext
	EntityManager em;

	@Bean
	OrderId.Provider orderIdProvider() {
		return OrderId.Provider.timeOrdered();
	}

    @Bean
    OrderRepository orderRepository(JpaRepository<OrderEntity, String> jpa) {
        return new DbOrderRepository(jpa);
    }

	@Bean
	InventoryRepository inventoryRepository(JpaRepository<InventoryEntity, String> jpa) {
		return new DbInventoryRepository(jpa);
	}

    @Bean
	InventoryService inventoryService(InventoryRepository inventoryRepository) {
        return new InventoryService(inventoryRepository);
    }

	@Bean
	OrderFactory orderFactory(OrderId.Provider provider) {
		return new OrderFactory(provider);
	}

    @Bean
    OrderService orderService(OrderFactory factory, OrderRepository orders, InventoryService inventoryService) {
        return new OrderService(factory, orders, inventoryService);
    }
}
