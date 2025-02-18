package tech.hoppr.shared;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SharedConfiguration {

	@Bean
	MessageBus messageBus(ApplicationEventPublisher publisher) {
		return new SpringMessageBus(publisher);
	}

}
