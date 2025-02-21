package tech.hoppr.order.port.spi;

import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.context.event.EventListener;
import tech.hoppr.order.model.OrderCancelled;
import tech.hoppr.order.model.OrderEvent;
import tech.hoppr.order.model.OrderPlaced;

@RequiredArgsConstructor
public class RabbitEventPublisher {

	private final RabbitTemplate client;

	@EventListener
	public void handle(OrderEvent event) {
		String eventName = switch (event) {
			case OrderCancelled ignored -> "order.cancelled";
			case OrderPlaced ignored -> "order.placed";
		};

		client.convertAndSend(eventName, event);
	}

}
