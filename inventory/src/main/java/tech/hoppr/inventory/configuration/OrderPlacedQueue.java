package tech.hoppr.inventory.configuration;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.QueueBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OrderPlacedQueue {

	public static final String ORDER_PLACED_QUEUE = "order.placed";
	public static final String ORDER_PLACED_QUEUE_DLX = ORDER_PLACED_QUEUE + ".dlx";
	public static final String ORDER_PLACED_QUEUE_DLQ = ORDER_PLACED_QUEUE + ".dlq";
	public static final String ORDER_PLACED_QUEUE_EXC = ORDER_PLACED_QUEUE + ".exc";

	@Bean
	Queue queueOrderPlaced() {
		return QueueBuilder.durable(ORDER_PLACED_QUEUE)
			.withArgument("x-dead-letter-exchange", ORDER_PLACED_QUEUE_DLX)
			.withArgument("x-dead-letter-routing-key", ORDER_PLACED_QUEUE_DLQ)
			.build();
	}

	@Bean
	DirectExchange orderPlacedExchange() {
		return new DirectExchange(ORDER_PLACED_QUEUE_EXC);
	}

	@Bean
	Binding orderPlacedBinding() {
		return BindingBuilder.bind(queueOrderPlaced())
			.to(orderPlacedExchange())
			.with(ORDER_PLACED_QUEUE_EXC);
	}

	@Bean
	Queue queueOrderPlacedDLQ() {
		return QueueBuilder.durable(ORDER_PLACED_QUEUE_DLQ)
			.build();
	}

	@Bean
	FanoutExchange queueOrderPlacedDLX() {
		return new FanoutExchange(ORDER_PLACED_QUEUE_DLX);
	}

	@Bean
	Binding orderPlacedDLQBinding() {
		return BindingBuilder
			.bind(queueOrderPlacedDLQ())
			.to(queueOrderPlacedDLX());
	}

}
