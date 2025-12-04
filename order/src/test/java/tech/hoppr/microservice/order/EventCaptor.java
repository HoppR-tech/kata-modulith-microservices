package tech.hoppr.microservice.order;

import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class EventCaptor {

	private final List<Object> capturedEvents = new ArrayList<>();

	public boolean hasCaptured(Object event) {
		return capturedEvents.contains(event);
	}

	@EventListener
	public void capture(Object event) {
		capturedEvents.add(event);
	}

}
