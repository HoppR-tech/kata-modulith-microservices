package tech.hoppr.microservice.order;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
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

	}

}
