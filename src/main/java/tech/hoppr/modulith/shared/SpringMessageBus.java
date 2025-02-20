package tech.hoppr.modulith.shared;

import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;

@RequiredArgsConstructor
public class SpringMessageBus implements MessageBus {

	private final ApplicationEventPublisher eventPublisher;

	@Override
	public void emit(Object message) {
		eventPublisher.publishEvent(message);
	}

}
