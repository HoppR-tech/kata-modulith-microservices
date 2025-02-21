package tech.hoppr.modulith.order.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.web.client.RestTemplate;

import tech.hoppr.modulith.order.model.OrderId;
import tech.hoppr.modulith.order.repository.OrderRepository;
import tech.hoppr.modulith.order.repository.jpa.DbOrderRepository;
import tech.hoppr.modulith.order.repository.jpa.OrderEntity;
import tech.hoppr.modulith.order.service.OrderFactory;
import tech.hoppr.modulith.order.service.OrderService;
import tech.hoppr.modulith.shared.MessageBus;

import java.time.Clock;

@Configuration
public class OrderConfiguration {

	@Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

	@Bean
	OrderId.Provider orderIdProvider() {
		return OrderId.Provider.timeOrdered();
	}

	@Bean
	OrderRepository orderRepository(JpaRepository<OrderEntity, String> jpa) {
		return new DbOrderRepository(jpa);
	}

	@Bean
	OrderFactory orderFactory(OrderId.Provider provider, Clock clock) {
		return new OrderFactory(provider, clock);
	}

	@Bean
	OrderService orderService(OrderFactory factory, OrderRepository orders, MessageBus messageBus, Clock clock) {
		return new OrderService(factory, orders, messageBus, clock);
	}

}
