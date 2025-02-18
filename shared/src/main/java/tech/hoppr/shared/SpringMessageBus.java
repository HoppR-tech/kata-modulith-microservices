package tech.hoppr.shared;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@ConditionalOnProperty(value = "message-bus.use", havingValue = "in-memory", matchIfMissing = true)
@Component
@RequiredArgsConstructor
public class SpringMessageBus implements MessageBus {

	private final ApplicationEventPublisher publisher;

	@Override
	public void emit(Object message) {
		publisher.publishEvent(message);
	}
}
