package tech.hoppr.modulith.order;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import tech.hoppr.modulith.order.repository.OrderRepository;
import tech.hoppr.modulith.order.service.OrderFactory;
import tech.hoppr.modulith.order.service.OrderService;
import tech.hoppr.modulith.shared.MessageEmitter;
import tech.hoppr.modulith.shared.OrderId;

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
