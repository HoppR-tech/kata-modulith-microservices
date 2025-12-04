package tech.hoppr.microservice.order;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import tech.hoppr.microservice.order.model.OrderId;
import tech.hoppr.microservice.order.repository.OrderRepository;
import tech.hoppr.microservice.order.service.OrderFactory;
import tech.hoppr.microservice.order.service.OrderService;
import tech.hoppr.microservice.order.shared.MessageEmitter;
import tech.hoppr.microservice.order.shared.SpringMessageEmitter;

import java.time.Clock;

@SpringBootApplication
public class OrderApplication {

	public static void main(String[] args) {
		SpringApplication.run(OrderApplication.class, args);
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

}
