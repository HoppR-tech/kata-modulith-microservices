package tech.hoppr.shared;

import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;

@RequiredArgsConstructor
public class SpringMessageBus implements MessageBus {

	private final ApplicationEventPublisher publisher;

	@Override
	public void emit(Object message) {
		publisher.publishEvent(message);
	}
}
