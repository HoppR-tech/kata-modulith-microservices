package tech.hoppr.shared;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

@ConditionalOnProperty(value = "message-bus.use", havingValue = "rabbitmq")
@Component
@RequiredArgsConstructor
public class RabbitMQMessageBus implements MessageBus {

	private static final ObjectMapper objectMapper = new ObjectMapper()
		.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);

	private final RabbitTemplate producer;

	@Override
	@SneakyThrows
	public void emit(Object message) {
		String payload = objectMapper.writeValueAsString(message);
		producer.convertAndSend("order.placed", payload);
	}
}
