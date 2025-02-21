package tech.hoppr.order.configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.JpaRepository;
import tech.hoppr.order.model.OrderId;
import tech.hoppr.order.port.spi.EventSerializerModule;
import tech.hoppr.order.port.spi.RabbitEventPublisher;
import tech.hoppr.order.repository.OrderRepository;
import tech.hoppr.order.repository.jpa.DbOrderRepository;
import tech.hoppr.order.repository.jpa.OrderEntity;
import tech.hoppr.order.service.OrderFactory;
import tech.hoppr.order.service.OrderService;
import tech.hoppr.shared.MessageBus;

import java.time.Clock;

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
	OrderFactory orderFactory(OrderId.Provider provider, Clock clock) {
		return new OrderFactory(provider, clock);
	}

	@Bean
	OrderService orderService(OrderFactory factory, OrderRepository orders, MessageBus messageBus, Clock clock) {
		return new OrderService(factory, orders, messageBus, clock);
	}

	@Bean
	RabbitEventPublisher eventPublisher(RabbitTemplate client) {
		return new RabbitEventPublisher(client);
	}

	@Bean
	MessageConverter messageConverter() {
		ObjectMapper objectMapper = new ObjectMapper()
			.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false)
			.registerModule(new JavaTimeModule())
			.registerModule(new EventSerializerModule());

		return new Jackson2JsonMessageConverter(objectMapper);
	}

}
