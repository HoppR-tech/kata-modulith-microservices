package tech.hoppr.modulith.order.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.JpaRepository;
import tech.hoppr.modulith.shared.OrderId;
import tech.hoppr.modulith.order.repository.jpa.OrderEntity;
import tech.hoppr.modulith.order.repository.jpa.DbOrderRepository;
import tech.hoppr.modulith.order.repository.OrderRepository;
import tech.hoppr.modulith.inventory.service.InventoryService;
import tech.hoppr.modulith.order.service.OrderFactory;
import tech.hoppr.modulith.order.service.OrderService;

@Configuration
public class OrderConfiguration {

	@Bean
	OrderId.Provider orderIdProvider() {
		return OrderId.Provider.timeOrdered();
	}

	@Bean
	OrderRepository orderRepository(JpaRepository<OrderEntity, String> jpa) {
		return new DbOrderRepository(jpa);
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
