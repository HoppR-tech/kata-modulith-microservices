package tech.hoppr.modulith.shared;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Clock;

@Configuration
public class SharedConfiguration {

	@Bean
	Clock clock() {
		return Clock.systemUTC();
	}

	@Bean
	MessageBus messageBus(ApplicationEventPublisher eventPublisher) {
		return new SpringMessageBus(eventPublisher);
	}

}
