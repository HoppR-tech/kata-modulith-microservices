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
public class OrderCancelledQueue {
	
	public static final String ORDER_CANCELLED_QUEUE = "order.cancelled";
	public static final String ORDER_CANCELLED_QUEUE_DLX = ORDER_CANCELLED_QUEUE + ".dlx";
	public static final String ORDER_CANCELLED_QUEUE_DLQ = ORDER_CANCELLED_QUEUE + ".dlq";
	public static final String ORDER_CANCELLED_QUEUE_EXC = ORDER_CANCELLED_QUEUE + ".exc";

	@Bean
	Queue queueOrderCancelled() {
		return QueueBuilder.durable(ORDER_CANCELLED_QUEUE)
			.withArgument("x-dead-letter-exchange", ORDER_CANCELLED_QUEUE_DLX)
			.withArgument("x-dead-letter-routing-key", ORDER_CANCELLED_QUEUE_DLQ)
			.build();
	}

	@Bean
	DirectExchange orderCancelledExchange() {
		return new DirectExchange(ORDER_CANCELLED_QUEUE_EXC);
	}

	@Bean
	Binding orderCancelledBinding() {
		return BindingBuilder.bind(queueOrderCancelled())
			.to(orderCancelledExchange())
			.with(ORDER_CANCELLED_QUEUE_EXC);
	}

	@Bean
	Queue queueOrderCancelledDLQ() {
		return QueueBuilder.durable(ORDER_CANCELLED_QUEUE_DLQ)
			.build();
	}

	@Bean
	FanoutExchange queueOrderCancelledDLX() {
		return new FanoutExchange(ORDER_CANCELLED_QUEUE_DLX);
	}

	@Bean
	Binding orderCancelledDLQBinding() {
		return BindingBuilder
			.bind(queueOrderCancelledDLQ())
			.to(queueOrderCancelledDLX());
	}
	
}
