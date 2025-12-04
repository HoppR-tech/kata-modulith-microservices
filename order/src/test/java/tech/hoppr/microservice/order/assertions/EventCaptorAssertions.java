package tech.hoppr.microservice.order.assertions;

import lombok.RequiredArgsConstructor;
import org.assertj.core.api.Assertions;
import tech.hoppr.microservice.order.EventCaptor;

@RequiredArgsConstructor
public class EventCaptorAssertions {

	private final EventCaptor captor;

	public EventCaptorAssertions hasCaptured(Object event) {
		Assertions.assertThat(captor.hasCaptured(event)).isTrue();
		return this;
	}

	public static EventCaptorAssertions assertThat(EventCaptor captor) {
		return new EventCaptorAssertions(captor);
	}

}
