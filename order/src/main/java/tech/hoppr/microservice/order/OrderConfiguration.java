package tech.hoppr.microservice.order;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import tech.hoppr.microservice.order.repository.OrderRepository;
import tech.hoppr.microservice.order.service.OrderFactory;
import tech.hoppr.microservice.order.service.OrderService;
import tech.hoppr.microservice.order.shared.MessageEmitter;
import tech.hoppr.microservice.order.shared.OrderId;

import java.time.Clock;

@Configuration
public class OrderConfiguration {

	@Bean
	OrderId.Provider orderIdProvider() {
		return OrderId.Provider.timeOrdered();
	}

	@Bean
	OrderFactory orderFactory(OrderId.Provider provider, Clock clock) {
		return new OrderFactory(provider, clock);
	}

	@Bean
	OrderService orderService(OrderFactory factory, OrderRepository orders, Clock clock, MessageEmitter messageEmitter) {
		return new OrderService(factory, orders, clock, messageEmitter);
	}

}
