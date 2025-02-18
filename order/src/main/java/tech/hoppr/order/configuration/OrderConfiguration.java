package tech.hoppr.order.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.JpaRepository;
import tech.hoppr.order.model.OrderId;
import tech.hoppr.order.repository.OrderRepository;
import tech.hoppr.order.repository.jpa.DbOrderRepository;
import tech.hoppr.order.repository.jpa.OrderEntity;
import tech.hoppr.order.service.OrderFactory;
import tech.hoppr.order.service.OrderService;
import tech.hoppr.shared.MessageBus;

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
	OrderService orderService(OrderFactory factory, OrderRepository orders, MessageBus messageBus) {
		return new OrderService(factory, orders, messageBus);
	}

}
