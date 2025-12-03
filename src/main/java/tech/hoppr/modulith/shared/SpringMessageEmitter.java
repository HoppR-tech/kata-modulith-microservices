package tech.hoppr.modulith.shared;

import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;

@RequiredArgsConstructor
public class SpringMessageEmitter implements MessageEmitter {

	private final ApplicationEventPublisher publisher;

	@Override
	public void emit(Object message) {
		publisher.publishEvent(message);
	}

}
