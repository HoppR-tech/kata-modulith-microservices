package tech.hoppr.microservice.order;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.support.WebClientAdapter;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;
import tech.hoppr.microservice.order.model.OrderId;
import tech.hoppr.microservice.order.repository.OrderRepository;
import tech.hoppr.microservice.order.service.OrderFactory;
import tech.hoppr.microservice.order.service.OrderService;
import tech.hoppr.microservice.order.shared.MessageEmitter;
import tech.hoppr.microservice.order.shared.SpringMessageEmitter;
import tech.hoppr.microservice.order.spi.inventory.on_order_placed.DecrementInventoryListener;
import tech.hoppr.microservice.order.spi.inventory.InventoryClient;

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

	@Configuration
	public static class PartnerConfiguration {

		@Bean
		public HttpServiceProxyFactory httpServiceProxyFactory(WebClient.Builder webClientBuilder, @Value("${partner.inventory.base-url}") String clientBaseUrl) {
			WebClient webClient = webClientBuilder
				.baseUrl(clientBaseUrl)
				.build();

			return HttpServiceProxyFactory.builderFor(WebClientAdapter.create(webClient))
				.build();
		}

		@Bean
		InventoryClient inventoryClient(HttpServiceProxyFactory factory) {
			return factory.createClient(InventoryClient.class);
		}

		@Bean
		DecrementInventoryListener decrementInventoryListener(InventoryClient inventoryClient) {
			return new DecrementInventoryListener(inventoryClient);
		}

	}

}
